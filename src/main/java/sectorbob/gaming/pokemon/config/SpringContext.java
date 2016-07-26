package sectorbob.gaming.pokemon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import sectorbob.gaming.pokemon.sms.EmailClient;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by ltm688 on 7/24/16.
 */
@Component
public class SpringContext {

    AppConfig appConfig;
    EmailClient emailClient;

    @Bean
    public AppConfig appConfig(ConfigLoader configLoader) throws FileNotFoundException {
        if(appConfig == null) {
            appConfig = configLoader.load();
            return appConfig;
        } else {
            return appConfig;
        }
    }

    @Bean
    public EmailClient emailClient(AppConfig appConfig) throws MessagingException {
        if(emailClient == null) {
            emailClient = new EmailClient(appConfig.getEmail().getUser(), appConfig.getEmail().getPassword());
            return emailClient;
        } else {
            return emailClient;
        }
    }

    @Bean(destroyMethod = "shutdown")
    public Executor taskScheduler() {
        return Executors.newScheduledThreadPool(3);
    }

}
