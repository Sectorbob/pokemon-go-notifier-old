package sectorbob.gaming.pokemon.model;

import POGOProtos.Map.Pokemon.MapPokemonOuterClass;
import POGOProtos.Map.Pokemon.NearbyPokemonOuterClass;
import POGOProtos.Map.Pokemon.WildPokemonOuterClass;
import com.pokegoapi.api.map.pokemon.NearbyPokemon;
import org.joda.time.Duration;

import java.util.Date;

/**
 * Created by ltm688 on 7/24/16.
 */
public class Pokemon {
    int no;
    String name;
    Double lat;
    Double lng;
    long encounterId;
    long expiryMillis;
    String generalLocation;

    public Pokemon(WildPokemonOuterClass.WildPokemon pokemon, String generalLocation) {
        setNo(pokemon.getPokemonData().getPokemonId().getNumber());
        setName(pokemon.getPokemonData().getPokemonId().getValueDescriptor().getName());
        setLat(pokemon.getLatitude());
        setLng(pokemon.getLongitude());
        setEncounterId(pokemon.getEncounterId());
        setExpiryMillis(System.currentTimeMillis() + pokemon.getTimeTillHiddenMs());
        setGeneralLocation(generalLocation);
    }

    public Pokemon(MapPokemonOuterClass.MapPokemon pokemon, String generalLocation) {
        setNo(pokemon.getPokemonId().getNumber());
        setName(pokemon.getPokemonId().getValueDescriptor().getName());
        setLat(pokemon.getLatitude());
        setLng(pokemon.getLongitude());
        setEncounterId(pokemon.getEncounterId());
        setExpiryMillis(pokemon.getExpirationTimestampMs());
        System.out.println(new Date(pokemon.getExpirationTimestampMs()));
        setGeneralLocation(generalLocation);
    }

    public Pokemon(NearbyPokemonOuterClass.NearbyPokemon pokemon, String generalLocation) {
        setNo(pokemon.getPokemonId().getNumber());
        setName(pokemon.getPokemonId().getValueDescriptor().getName());
        setLat(null);
        setLng(null);
        setEncounterId(pokemon.getEncounterId());
        setExpiryMillis(Long.MAX_VALUE);
        setGeneralLocation(generalLocation);
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public long getEncounterId() {
        return encounterId;
    }

    public void setEncounterId(long encounterId) {
        this.encounterId = encounterId;
    }

    public long getExpiryMillis() {
        return expiryMillis;
    }

    public void setExpiryMillis(long expiryMillis) {
        this.expiryMillis = expiryMillis;
    }

    public String getGeneralLocation() {
        return generalLocation;
    }

    public void setGeneralLocation(String generalLocation) {
        this.generalLocation = generalLocation;
    }

    // Helper Method

    public void update(double lat, double lng, long expiryMillis) {
        setLat(lat);
        setLng(lng);
        setExpiryMillis(expiryMillis);
    }

    public boolean isFound() {
        return getLat() != null && getLng() != null && getExpiryMillis() != Long.MAX_VALUE;
    }

    // Overridden Methods

    public boolean equals(Object o) {
        return o != null &&
                o.getClass().isAssignableFrom(Pokemon.class) &&
                ((Pokemon)o).getEncounterId() == getEncounterId();
    }

    public int hashCode() {
        return getNo() + getName().hashCode();
    }

    public String toString() {
        StringBuilder s = new StringBuilder();

        s.append(getName());
        while(s.toString().length() < 13) { s.append(" "); }
        s.append("  ");
        s.append("EncounterId: ").append(getEncounterId());
        s.append("  ");
        s.append("Expiry: ");
        Duration expiresIn = new Duration(Math.max(getExpiryMillis() - System.currentTimeMillis(), 0));

        s.append(expiresIn.getStandardMinutes()).append("m ").append(expiresIn.getStandardSeconds() % 60).append("s");

        s.append("   -   ").append(getGeneralLocation());

        return s.toString();
    }
}
