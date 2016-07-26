package sectorbob.gaming.pokemon.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sectorbob.gaming.pokemon.model.SoughtAfterPokemon;

/**
 * Created by ltm688 on 7/25/16.
 */
public interface SoughtAfterPokemonRepository extends JpaRepository<SoughtAfterPokemon, Long> {

    @Query(value = "SELECT p FROM SoughtAfterPokemon p WHERE p.notificationSent = true AND p.encounterId = :encounterId")
    SoughtAfterPokemon notificationSentForPokemon(@Param("encounterId") Long encounterId);
}
