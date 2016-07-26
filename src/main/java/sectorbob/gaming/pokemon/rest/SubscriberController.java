package sectorbob.gaming.pokemon.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sectorbob.gaming.pokemon.model.Subscriber;
import sectorbob.gaming.pokemon.repo.SubscriberRepository;

import java.util.List;

/**
 * Created by ltm688 on 7/26/16.
 */
@RestController
@RequestMapping(value  = "/subscribers")
public class SubscriberController {


    @Autowired
    SubscriberRepository subscriberRepository;

    @RequestMapping(value = "/{username}")
    public Subscriber getSubscriber(@PathVariable(value="username") String username) {
        Subscriber subscriber = subscriberRepository.findByUsername(username);
        return subscriber;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Subscriber createSubscriber(@RequestBody Subscriber subscriber) {

        if(subscriberRepository.exists(subscriber.getUsername())) {
            throw new RuntimeException("user already exists");
        }
        return subscriberRepository.save(subscriber);
    }

    @RequestMapping(value = "/{username}/pokemonOfInterest", method = RequestMethod.POST)
    public Subscriber updatePokemonOfInterest(@PathVariable(value="username") String username,
                                              @RequestBody List<String> pokemon) {
        Subscriber subscriber = subscriberRepository.findByUsername(username);
        if(subscriber == null) {
            throw new RuntimeException("username not found");
        }
        subscriber.setPokemonOfInterest(pokemon);
        subscriberRepository.save(subscriber);
        return subscriber;
    }

    @RequestMapping(value = "/{username}/locationsOfInterest", method = RequestMethod.POST)
    public Subscriber updateLocationsOfInterest(@PathVariable(value="username") String username,
                                                @RequestBody List<String> locations) {
        Subscriber subscriber = subscriberRepository.findByUsername(username);
        if(subscriber == null) {
            throw new RuntimeException("username not found");
        }
        subscriber.setLocationsOfInterest(locations);
        subscriberRepository.save(subscriber);
        return subscriber;
    }

}
