package sectorbob.gaming.pokemon.sms;

// You may want to be more specific in your imports
import com.twilio.sdk.*;
import com.twilio.sdk.resource.factory.*;
import com.twilio.sdk.resource.instance.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sectorbob.gaming.pokemon.config.AppConfig;
import sectorbob.gaming.pokemon.model.Pokemon;
import sectorbob.gaming.pokemon.util.Util;

/**
 * Created by ltm688 on 7/24/16.
 */
@Component
public class TwilioClient {

    @Autowired
    AppConfig appConfig;

    public void send(Pokemon pokemon) throws TwilioRestException {
        TwilioRestClient client = new TwilioRestClient(appConfig.getTwilio().getAccountSid(),
                appConfig.getTwilio().getAuthToken());

        for(AppConfig.Subscriber subscriber : appConfig.getSubscribers()) {
            System.out.println("Sending message to " + subscriber);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("To", subscriber.getNumber()));
            params.add(new BasicNameValuePair("From", appConfig.getTwilio().getSenderNumber()));
            params.add(new BasicNameValuePair("Body", pokemon.getName() + " spotted. expires at " +
                    Util.getExpiryTime(pokemon.getExpiryMillis()) + " Near  " + pokemon.getGeneralLocation() + " " + Util.generateGoogleMapsLink(pokemon)
            ));

            MessageFactory messageFactory = client.getAccount().getMessageFactory();
            try {
                Message message = messageFactory.create(params);
                System.out.println("SMS Message Sent to " + subscriber + ", SID:" + message.getSid());
            } catch (TwilioRestException e) {
                System.err.println("Unable to sent SMS message to " + subscriber + " Error:" + e.getErrorMessage());
            }
        }

    }
}