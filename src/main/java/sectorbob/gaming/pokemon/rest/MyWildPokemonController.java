package sectorbob.gaming.pokemon.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    public List<Pokemon> getPokemon(@RequestParam(value = "expired", required = false) Boolean expired,
                                    @RequestParam(value = "area", required = false) String area) {
        if(expired == null && area != null) {
            return wildPokemonRepository.findByGeneralLocation(area);
        } else if(expired != null && area == null) {
            return wildPokemonRepository.findByExpired(expired);
        } else if(expired != null && area != null) {
            return wildPokemonRepository.findByExpiredAndGeneralLocation(expired, area);
        } else {
            return wildPokemonRepository.findAll();
        }
    }

    @RequestMapping(value="/pokemon/{name}")
    public List<Pokemon> getSpecificPokemon(@PathVariable(value = "name") String name,
                                            @RequestParam(value = "expired", required = false) Boolean expired) {
        if(expired == null) {
            return wildPokemonRepository.findByName(name);
        } else {
            return wildPokemonRepository.findByExpiredAndName(expired, name);
        }
    }

}
