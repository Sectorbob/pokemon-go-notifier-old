package sectorbob.gaming.pokemon.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sectorbob.gaming.pokemon.model.Pokemon;

import java.util.List;

/**
 * Keeps a sorted list of wild pokemon
 *
 * Created by ltm688 on 7/24/16.
 */
public interface WildPokemonRepository extends JpaRepository<Pokemon, Long> {

    List<Pokemon> findByExpired(boolean expired);

    List<Pokemon> findByName(String name);

    List<Pokemon> findByExpiredAndName(boolean expired, String name);

    List<Pokemon> findByGeneralLocation(String generalLocation);

    List<Pokemon> findByExpiredAndGeneralLocation(boolean expired, String generalLocation);

}
