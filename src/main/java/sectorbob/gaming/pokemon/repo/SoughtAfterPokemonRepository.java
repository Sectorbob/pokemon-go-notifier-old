package sectorbob.gaming.pokemon.repo;

import org.springframework.stereotype.Component;
import sectorbob.gaming.pokemon.model.Pokemon;
import sectorbob.gaming.pokemon.model.SoughtAfterPokemon;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ltm688 on 7/25/16.
 */
@Component
public class SoughtAfterPokemonRepository {

    private List<SoughtAfterPokemon> soughtAfterPokemons = new ArrayList<SoughtAfterPokemon>();

    public void add(SoughtAfterPokemon soughtAfterPokemon) {
        soughtAfterPokemons.add(soughtAfterPokemon);
    }

    public boolean notificationSentForPokemon(Pokemon pokemon) {
        for(SoughtAfterPokemon soughtAfterPokemon : soughtAfterPokemons) {
            if(soughtAfterPokemon.getPokemon().equals(pokemon) && soughtAfterPokemon.isNotificationSent()) {
                return true;
            }
        }
        return false;
    }
}
