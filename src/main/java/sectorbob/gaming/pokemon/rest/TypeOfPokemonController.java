package sectorbob.gaming.pokemon.rest;

import POGOProtos.Enums.PokemonIdOuterClass;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sectorbob.gaming.pokemon.model.TypeOfPokemon;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ltm688 on 7/28/16.
 */
@RestController
public class TypeOfPokemonController {

    @RequestMapping(value = "/types")
    public List<TypeOfPokemon> getTypes() {

        List<TypeOfPokemon> typesOfPokemon = new ArrayList<>();

        for(int i = 1; i <= 151; i++) {
            PokemonIdOuterClass.PokemonId id = PokemonIdOuterClass.PokemonId.forNumber(i);
            String name = id.getValueDescriptor().getName();
            typesOfPokemon.add(new TypeOfPokemon(i, name));
        }

        return typesOfPokemon;

    }
}
