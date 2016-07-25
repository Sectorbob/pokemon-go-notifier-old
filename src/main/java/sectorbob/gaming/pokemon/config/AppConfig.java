package sectorbob.gaming.pokemon.config;

import java.util.List;

/**
 * Created by ltm688 on 7/24/16.
 */
public class AppConfig {

    String googleMapsApiToken;
    String pogoAuthType;
    String pogoUsername;
    String pogoPassword;
    List<Local> locals;
    List<String> seekPokemon;
    List<String> ignorePokemon;
    int pollFrequencyMillis;

    DistanceSettings distanceSettings;

    Twilio twilio;

    public String getGoogleMapsApiToken() {
        return googleMapsApiToken;
    }

    public void setGoogleMapsApiToken(String googleMapsApiToken) {
        this.googleMapsApiToken = googleMapsApiToken;
    }

    public String getPogoAuthType() {
        return pogoAuthType;
    }

    public void setPogoAuthType(String pogoAuthType) {
        this.pogoAuthType = pogoAuthType;
    }

    public String getPogoUsername() {
        return pogoUsername;
    }

    public void setPogoUsername(String pogoUsername) {
        this.pogoUsername = pogoUsername;
    }

    public String getPogoPassword() {
        return pogoPassword;
    }

    public void setPogoPassword(String pogoPassword) {
        this.pogoPassword = pogoPassword;
    }

    public List<Local> getLocals() {
        return locals;
    }

    public void setLocals(List<Local> locals) {
        this.locals = locals;
    }

    public List<String> getSeekPokemon() {
        return seekPokemon;
    }

    public void setSeekPokemon(List<String> seekPokemon) {
        this.seekPokemon = seekPokemon;
    }

    public List<String> getIgnorePokemon() {
        return ignorePokemon;
    }

    public void setIgnorePokemon(List<String> ignorePokemon) {
        this.ignorePokemon = ignorePokemon;
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

    public Twilio getTwilio() {
        return twilio;
    }

    public void setTwilio(Twilio twilio) {
        this.twilio = twilio;
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

    public static class Twilio {
        String accountSid;
        String authToken;
        String senderNumber;
        List<Subscriber> subscribers;

        public String getAccountSid() {
            return accountSid;
        }

        public void setAccountSid(String accountSid) {
            this.accountSid = accountSid;
        }

        public String getAuthToken() {
            return authToken;
        }

        public void setAuthToken(String authToken) {
            this.authToken = authToken;
        }

        public String getSenderNumber() {
            return senderNumber;
        }

        public void setSenderNumber(String senderNumber) {
            this.senderNumber = senderNumber;
        }

        public List<Subscriber> getSubscribers() {
            return subscribers;
        }

        public void setSubscribers(List<Subscriber> subscribers) {
            this.subscribers = subscribers;
        }
    }

    public static class Subscriber {
        String name;
        String number;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String toString() {
            StringBuilder s = new StringBuilder();
            s.append("(").append(this.getName()).append(",").append(this.getNumber()).append(")");
            return s.toString();
        }
    }
}
