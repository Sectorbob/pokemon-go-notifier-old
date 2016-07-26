package sectorbob.gaming.pokemon.model;

import sectorbob.gaming.pokemon.config.AppConfig;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import java.util.List;

/**
 * Created by ltm688 on 7/26/16.
 */
@Entity
public class Subscriber {

    @Id
    String username;
    String name;
    String contact;
    String carrier;
    @ElementCollection(fetch = FetchType.EAGER)
    List<String> locationsOfInterest;
    @ElementCollection(fetch = FetchType.EAGER)
    List<String> pokemonOfInterest;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public List<String> getLocationsOfInterest() {
        return locationsOfInterest;
    }

    public void setLocationsOfInterest(List<String> locationsOfInterest) {
        this.locationsOfInterest = locationsOfInterest;
    }

    public List<String> getPokemonOfInterest() {
        return pokemonOfInterest;
    }

    public void setPokemonOfInterest(List<String> pokemonOfInterest) {
        this.pokemonOfInterest = pokemonOfInterest;
    }

    public boolean interestedIn(Pokemon pokemon) {
        return interestedInArea(pokemon.getGeneralLocation()) && interestedInPokemon(pokemon.getName());
    }

    private boolean interestedInArea(String area) {
        return this.getLocationsOfInterest().contains(area) || this.getLocationsOfInterest().contains("all");
    }

    private boolean interestedInPokemon(String name) {
        return this.getPokemonOfInterest().contains(name) || this.getPokemonOfInterest().contains("all");
    }
}
