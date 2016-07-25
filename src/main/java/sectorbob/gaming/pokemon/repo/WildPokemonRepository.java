package sectorbob.gaming.pokemon.repo;

import org.springframework.stereotype.Component;
import sectorbob.gaming.pokemon.model.Pokemon;

import java.util.ArrayList;
import java.util.List;

/**
 * Keeps a sorted list of wild pokemon
 *
 * Created by ltm688 on 7/24/16.
 */
@Component
public class WildPokemonRepository {

    private List<Pokemon> wildPokemon;

    public WildPokemonRepository() {
        wildPokemon = new ArrayList<Pokemon>();
    }

    public synchronized List<Pokemon> getAll() {
        return new ArrayList<Pokemon>(wildPokemon);
    }

    public synchronized boolean insert(Pokemon pokemon) {
        if(wildPokemon.contains(pokemon)) {
            return false;
        }

        long expiry = pokemon.getExpiryMillis();

        int currentIndex = 0;
        for(Pokemon entry : wildPokemon) {

            if(entry.getExpiryMillis() > expiry) {
                wildPokemon.add(currentIndex, pokemon);
                return true;
            }
            currentIndex++;
        }
        wildPokemon.add(pokemon);

        return true;
    }

    public synchronized void remove(Pokemon pokemon) {
        wildPokemon.remove(pokemon);
    }

    public synchronized List<Pokemon> removeExpiredPokemon() {
        List<Pokemon> toBeRemoved = new ArrayList<Pokemon>();

        for(Pokemon entry : wildPokemon) {
            if(entry.getExpiryMillis() < System.currentTimeMillis()) {
                toBeRemoved.add(entry);
            }
        }

        wildPokemon.removeAll(toBeRemoved);

        return toBeRemoved;
    }

}
