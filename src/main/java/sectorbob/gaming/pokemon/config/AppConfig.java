package sectorbob.gaming.pokemon.config;

import java.util.List;

/**
 * Created by ltm688 on 7/24/16.
 */
public class AppConfig {

    Email email;
    String googleMapsApiToken;
    PokemonGoLogin pogoAccount1;
    PokemonGoLogin pogoAccount2;
    List<Local> locals;
    List<String> ignorePokemon;
    int pollFrequencyMillis;
    int bufferBetweenMovesMillis;
    DistanceSettings distanceSettings;

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public String getGoogleMapsApiToken() {
        return googleMapsApiToken;
    }

    public void setGoogleMapsApiToken(String googleMapsApiToken) {
        this.googleMapsApiToken = googleMapsApiToken;
    }

    public PokemonGoLogin getPogoAccount2() {
        return pogoAccount2;
    }

    public void setPogoAccount2(PokemonGoLogin pogoAccount2) {
        this.pogoAccount2 = pogoAccount2;
    }

    public PokemonGoLogin getPogoAccount1() {
        return pogoAccount1;
    }

    public void setPogoAccount1(PokemonGoLogin pogoAccount1) {
        this.pogoAccount1 = pogoAccount1;
    }

    public List<Local> getLocals() {
        return locals;
    }

    public void setLocals(List<Local> locals) {
        this.locals = locals;
    }

    public List<String> getIgnorePokemon() {
        return ignorePokemon;
    }

    public void setIgnorePokemon(List<String> ignorePokemon) {
        this.ignorePokemon = ignorePokemon;
    }

    public int getBufferBetweenMovesMillis() {
        return bufferBetweenMovesMillis;
    }

    public void setBufferBetweenMovesMillis(int bufferBetweenMovesMillis) {
        this.bufferBetweenMovesMillis = bufferBetweenMovesMillis;
    }

    public int getPollFrequencyMillis() {
        return pollFrequencyMillis;
    }

    public void setPollFrequencyMillis(int pollFrequencyMillis) {
        this.pollFrequencyMillis = pollFrequencyMillis;
    }

    public DistanceSettings getDistanceSettings() {
        return distanceSettings;
    }

    public void setDistanceSettings(DistanceSettings distanceSettings) {
        this.distanceSettings = distanceSettings;
    }

    public static class DistanceSettings {
        Double wildPokemonMaxRadius;

        public Double getWildPokemonMaxRadius() {
            return wildPokemonMaxRadius;
        }

        public void setWildPokemonMaxRadius(Double wildPokemonMaxRadius) {
            this.wildPokemonMaxRadius = wildPokemonMaxRadius;
        }
    }

    public static class PokemonGoLogin {
        String authType;
        String username;
        String password;

        public String getAuthType() {
            return authType;
        }

        public void setAuthType(String authType) {
            this.authType = authType;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public static class Local {

        String name;
        List<Location> locations;
        Boolean enabled;

        public Boolean getEnabled() {
            return enabled;
        }

        public void setEnabled(Boolean enabled) {
            this.enabled = enabled;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Location> getLocations() {
            return locations;
        }

        public void setLocations(List<Location> locations) {
            this.locations = locations;
        }
    }

    public static class Location {

        String name;
        String googleQuery;
        Double latitude;
        Double longitude;
        Double altitude;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGoogleQuery() {
            return googleQuery;
        }

        public void setGoogleQuery(String googleQuery) {
            this.googleQuery = googleQuery;
        }

        public Double getLatitude() {
            return latitude;
        }

        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }

        public Double getLongitude() {
            return longitude;
        }

        public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }

        public Double getAltitude() {
            return altitude;
        }

        public void setAltitude(Double altitude) {
            this.altitude = altitude;
        }
    }

    public static class Email {
        String user;
        String password;

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

}
