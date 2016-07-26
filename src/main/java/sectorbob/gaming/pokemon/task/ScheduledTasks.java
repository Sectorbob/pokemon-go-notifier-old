package sectorbob.gaming.pokemon.task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import POGOProtos.Map.Pokemon.MapPokemonOuterClass;
import POGOProtos.Map.Pokemon.NearbyPokemonOuterClass;
import POGOProtos.Map.Pokemon.WildPokemonOuterClass;
import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.model.LatLng;
import com.google.maps.model.PlacesSearchResult;
import com.pokegoapi.api.PokemonGo;
import com.pokegoapi.auth.GoogleLogin;
import com.pokegoapi.exceptions.LoginFailedException;
import com.pokegoapi.exceptions.RemoteServerException;
import com.twilio.sdk.TwilioRestException;
import okhttp3.OkHttpClient;
import org.joda.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sectorbob.gaming.pokemon.config.AppConfig;
import sectorbob.gaming.pokemon.model.Pokemon;
import sectorbob.gaming.pokemon.model.SoughtAfterPokemon;
import sectorbob.gaming.pokemon.repo.SoughtAfterPokemonRepository;
import sectorbob.gaming.pokemon.repo.WildPokemonRepository;
import sectorbob.gaming.pokemon.sms.EmailClient;
import sectorbob.gaming.pokemon.sms.TwilioClient;

@Component
public class ScheduledTasks {

    @Autowired
    WildPokemonRepository wildPokemonRepository;

    @Autowired
    SoughtAfterPokemonRepository soughtAfterPokemonRepository;

    @Autowired
    TwilioClient twilioClient;

    @Autowired
    EmailClient emailClient;

    @Autowired
    AppConfig appConfig;

    private OkHttpClient httpClient;
    private GoogleLogin login;
    private PokemonGo go;

    public ScheduledTasks() throws LoginFailedException, RemoteServerException {
        httpClient = new OkHttpClient();
        login = new GoogleLogin(httpClient);
        go = new PokemonGo(login.login(), httpClient);

        System.out.println("Username: " + go.getPlayerProfile().getUsername());
    }

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 60000)
    public void scanForNewPokemon() throws LoginFailedException, RemoteServerException {
        long startMillis = System.currentTimeMillis();

        for(AppConfig.Local local : appConfig.getLocals()) {
            if(local.getEnabled()) {
                for (AppConfig.Location location : local.getLocations()) {

                    System.out.println("Checking Location: " + location.getName() + " | " + location.getLatitude() +
                            "," + location.getLongitude());

                    go.setLocation(location.getLatitude(), location.getLongitude(), location.getAltitude());


                    for(Pokemon pokemon : findNearbyPokemon(local, location)) {
                        if( ! wildPokemonRepository.exists(pokemon.getEncounterId())) {
                            wildPokemonRepository.save(pokemon);
                            System.out.println("New Nearby Pokemon: " + pokemon);
                        }
                    }
                }
            }
        }

        long endMillis = System.currentTimeMillis();

        Duration cycleDuration = new Duration(endMillis-startMillis);
        System.out.println("Cycle Duration: " + cycleDuration.getStandardMinutes() + "m "
                + cycleDuration.getStandardSeconds() % 60 + "s");

        System.out.println("Catchable Pokemon:");
        for(Pokemon pokemon : wildPokemonRepository.findByExpired(false)) {
            if(! ignorePokemon(pokemon))
                System.out.println(pokemon);
        }

    }

    @Scheduled(fixedRate = 1000)
    public void removeExpiredWildPokemon() {
        List<Pokemon> expiredPokemon = new ArrayList<Pokemon>();

        for(Pokemon wildPokemon : wildPokemonRepository.findByExpired(false)) {
            if(wildPokemon.getExpiryMillis() < System.currentTimeMillis()) {
                wildPokemon.setExpired(true);
                expiredPokemon.add(wildPokemon);
            }
        }

        // Update database
        if(!expiredPokemon.isEmpty()) {
            wildPokemonRepository.save(expiredPokemon);
            System.out.println("Removed " + expiredPokemon.size() + " expired pokemon.");
        }
    }

    @Scheduled(fixedRate = 5000)
    public void detectNewSoughtPokemon() throws TwilioRestException {
        for(Pokemon pokemon : wildPokemonRepository.findByExpired(false)) {
            if(pokemonIsSoughtAfter(pokemon) && soughtAfterPokemonRepository.notificationSentForPokemon(pokemon.getEncounterId()) == null) {

                for(AppConfig.Subscriber subscriber : appConfig.getSubscribers()) {
                    emailClient.send(pokemon, subscriber);
                }

                SoughtAfterPokemon soughtAfterPokemon = new SoughtAfterPokemon(pokemon);
                soughtAfterPokemon.setNotificationSent(true);
                soughtAfterPokemonRepository.save(soughtAfterPokemon);
            }
        }
    }
    // Helper methods

    public List<Pokemon> getNearbyPokemon(AppConfig.Local local) throws LoginFailedException, RemoteServerException {
        List<Pokemon> nearbyPokemon = new ArrayList<Pokemon>();

        for (NearbyPokemonOuterClass.NearbyPokemon pokemon : go.getMap().getMapObjects().getNearbyPokemons()) {
            Pokemon pokemon1 = new Pokemon(pokemon, local.getName());
            nearbyPokemon.add(pokemon1);
            //System.out.println("Nearby Distance: " + pokemon.getDistanceInMeters() + "m");
        }
        return nearbyPokemon;
    }

    public List<Pokemon> getWildPokemon(AppConfig.Local local, AppConfig.Location location) throws LoginFailedException, RemoteServerException {
        go.setLocation(location.getLatitude(), location.getLongitude(), location.getAltitude());
        try { Thread.sleep(100); } catch (InterruptedException e) { }

        List<Pokemon> wildPokemon = new ArrayList<Pokemon>();

        for (WildPokemonOuterClass.WildPokemon pokemon : go.getMap().getMapObjects().getWildPokemons()) {
            //for (MapPokemonOuterClass.MapPokemon pokemon : go.getMap().getMapObjects().getCatchablePokemons()) {
            //for (NearbyPokemonOuterClass.NearbyPokemon pokemon : go.getMap().getMapObjects().getNearbyPokemons()) {
            //System.out.println("Catchable Distance: " + distance(location.getLatitude(), pokemon.getLatitude(),
            //        location.getLongitude(), pokemon.getLongitude(), 0.0, 0.0) + "m");
            Pokemon pokemon1 = new Pokemon(pokemon, local.getName());
            wildPokemon.add(pokemon1);
        }

        return wildPokemon;
    }

    public List<Pokemon> findNearbyPokemon(AppConfig.Local local, AppConfig.Location location) throws LoginFailedException, RemoteServerException {

        // Detect Nearby pokemon
        List<Pokemon> nearbyPokemon = new ArrayList<Pokemon>();

        // Probe for new pokemon locations
        // Initial Spot
        addNewWildPokemon(nearbyPokemon, location, local, "Center");

        AppConfig.Location newLocation;
        double radius = appConfig.getDistanceSettings().getWildPokemonMaxRadius();

        // Continue to the Top right if not all found
        newLocation = moveLocation(location, radius, radius);
        addNewWildPokemon(nearbyPokemon, newLocation, local, "Top Right");

        newLocation = moveLocation(location, radius*-1, radius);
        addNewWildPokemon(nearbyPokemon, newLocation, local, "Top Left");

        newLocation = moveLocation(location, radius*-1, radius*-1);
        addNewWildPokemon(nearbyPokemon, newLocation, local, "Bottom Left");

        newLocation = moveLocation(location, radius, radius*-1);
        addNewWildPokemon(nearbyPokemon, newLocation, local, "Bottom Right");

        newLocation = moveLocation(location, radius*2, radius*2);
        addNewWildPokemon(nearbyPokemon, newLocation, local, "Far Top Right");

        newLocation = moveLocation(location, 0, radius*2);
        addNewWildPokemon(nearbyPokemon, newLocation, local, "Far Top");

        newLocation = moveLocation(location, radius*-2, radius*2);
        addNewWildPokemon(nearbyPokemon, newLocation, local, "Far Top Left");

        newLocation = moveLocation(location, radius*-2, 0);
        addNewWildPokemon(nearbyPokemon, newLocation, local, "Far Left");

        newLocation = moveLocation(location, radius*-2, radius*-2);
        addNewWildPokemon(nearbyPokemon, newLocation, local, "Far Bottom Left");

        newLocation = moveLocation(location, 0, radius*-2);
        addNewWildPokemon(nearbyPokemon, newLocation, local, "Far Bottom");

        newLocation = moveLocation(location, radius*2, radius*-2);
        addNewWildPokemon(nearbyPokemon, newLocation, local, "Far Bottom Right");

        newLocation = moveLocation(location, radius*-2, 0);
        addNewWildPokemon(nearbyPokemon, newLocation, local, "Far Right");

        newLocation = moveLocation(location, radius, radius*3);
        addNewWildPokemon(nearbyPokemon, newLocation, local, "Far Top Top Right");

        newLocation = moveLocation(location, radius*-1, radius*3);
        addNewWildPokemon(nearbyPokemon, newLocation, local, "Far Top Top Left");

        newLocation = moveLocation(location, radius*-3, radius);
        addNewWildPokemon(nearbyPokemon, newLocation, local, "Far Top Left Left");

        newLocation = moveLocation(location, radius*-3, radius*-1);
        addNewWildPokemon(nearbyPokemon, newLocation, local, "Far Bottom Left Left");

        newLocation = moveLocation(location, radius*-1, radius*-3);
        addNewWildPokemon(nearbyPokemon, newLocation, local, "Far Bottom Bottom Left");

        newLocation = moveLocation(location, radius, radius*-3);
        addNewWildPokemon(nearbyPokemon, newLocation, local, "Far Bottom Bottom Right");

        newLocation = moveLocation(location, radius*3, radius*-1);
        addNewWildPokemon(nearbyPokemon, newLocation, local, "Far Bottom Right Right");

        newLocation = moveLocation(location, radius*3, radius);
        addNewWildPokemon(nearbyPokemon, newLocation, local, "Far Top Right Right");

        return nearbyPokemon;

    }

    public void addNewWildPokemon(List<Pokemon> nearbyPokemon, AppConfig.Location location, AppConfig.Local local, String area) throws LoginFailedException, RemoteServerException {
        for(Pokemon wildPokemon : getWildPokemon(local, location)) {
            if(! nearbyPokemon.contains(wildPokemon)) {
                nearbyPokemon.add(wildPokemon);
                //System.out.println("Added " + wildPokemon.getName() + " from " + area + " in " + local.getName());
            }
        }
    }

    public boolean pokemonIsSoughtAfter(Pokemon pokemon) {
        for(String soughtPokemonName : appConfig.getSeekPokemon()) {
            if(soughtPokemonName.equals(pokemon.getName())) {
                return true;
            }
        }
        return false;
    }

    public PlacesSearchResult getLocation(String location) throws Exception {
        GeoApiContext context = new GeoApiContext();
        context.setApiKey(appConfig.getGoogleMapsApiToken());

        for(PlacesSearchResult result : PlacesApi.textSearchQuery(context, location).await().results) {
            return result;
        }

        return null;
    }

    public boolean ignorePokemon(Pokemon pokemon) {
        for(String po : appConfig.getIgnorePokemon()) {
            if(po.equalsIgnoreCase(pokemon.getName())) {
                return true;
            }
        }
        return false;
    }

    /*
     * Calculate distance between two points in latitude and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0.0. Uses Haversine method as its base.
     *
     * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
     * el2 End altitude in meters
     * @returns Distance in Meters
     */
    public static double distance(double lat1, double lat2, double lon1,
                                  double lon2, double el1, double el2) {

        final int R = 6371; // Radius of the earth

        Double latDistance = Math.toRadians(lat2 - lat1);
        Double lonDistance = Math.toRadians(lon2 - lon1);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }

    /**
     *
     * @param dx horizontal diplacement in meters
     * @param dy vertical displacement in meters
     * @return new coordinates
     */
    public static AppConfig.Location moveLocation(AppConfig.Location location, double dx, double dy) {
        double lat = location.getLatitude() + (180/Math.PI)*(dy/6378137);
        double lon = location.getLongitude() + (180/Math.PI)*(dx/6378137)/Math.cos(location.getLatitude());
        AppConfig.Location newLocation = new AppConfig.Location();
        newLocation.setLatitude(lat);
        newLocation.setLongitude(lon);
        newLocation.setAltitude(0.0);
        return newLocation;
    }
}