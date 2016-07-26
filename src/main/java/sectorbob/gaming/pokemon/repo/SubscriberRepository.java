package sectorbob.gaming.pokemon.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sectorbob.gaming.pokemon.model.Subscriber;

/**
 * Created by ltm688 on 7/26/16.
 */
public interface SubscriberRepository extends JpaRepository<Subscriber, String> {

    Subscriber findByUsername(String username);

}
