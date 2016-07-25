package sectorbob.gaming.pokemon.sms;

// You may want to be more specific in your imports
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;
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

        for(String recipient : appConfig.getTwilio().getRecipients()) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("To", recipient));
            params.add(new BasicNameValuePair("From", appConfig.getTwilio().getSenderNumber()));
            params.add(new BasicNameValuePair("Body", pokemon.getName() + " spotted. expires at " +
                    new Date(pokemon.getExpiryMillis()) + " here: " + generateGoogleMapsLink(pokemon)
            ));

            MessageFactory messageFactory = client.getAccount().getMessageFactory();
            Message message = messageFactory.create(params);
            System.out.println(message.getSid());
        }

    }


    public static String generateGoogleMapsLink(Pokemon pokemon) {
        StringBuilder s = new StringBuilder();

        s.append("http://maps.google.com/maps?z=12&t=m&q=loc:").append(round(pokemon.getLat())).append("+")
                .append(round(pokemon.getLng()));

        return s.toString();
    }

    public static String round(double d) {
        DecimalFormat df = new DecimalFormat("#.#######");
        df.setRoundingMode(RoundingMode.CEILING);
        return df.format(d);
    }
}