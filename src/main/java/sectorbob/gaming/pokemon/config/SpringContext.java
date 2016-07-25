package sectorbob.gaming.pokemon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;

/**
 * Created by ltm688 on 7/24/16.
 */
@Component
public class SpringContext {

    AppConfig appConfig;

    @Bean
    public AppConfig appConfig(ConfigLoader configLoader) throws FileNotFoundException {
        if(appConfig == null) {
            return configLoader.load();
        } else {
            return appConfig;
        }
    }

}
