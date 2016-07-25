package sectorbob.gaming.pokemon.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sectorbob.gaming.pokemon.model.Pokemon;
import sectorbob.gaming.pokemon.repo.WildPokemonRepository;

import java.util.List;

/**
 * Created by ltm688 on 7/24/16.
 */
@RestController
public class MyWildPokemonController {

    @Autowired
    WildPokemonRepository wildPokemonRepository;

    @RequestMapping(value="/pokemon", method = RequestMethod.GET)
    public List<Pokemon> getPokemon() {
        return wildPokemonRepository.getAll();
    }

}
