package sectorbob.gaming.pokemon.util;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import sectorbob.gaming.pokemon.model.Pokemon;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by ltm688 on 7/25/16.
 */
public class Util {

    static DateTimeFormatter formatter = DateTimeFormat.forPattern("HH:mm:ss zzz");

    public static String getExpiryTime(long millis) {
        boolean isDST = TimeZone.getTimeZone("US/New York").inDaylightTime(new Date());
        TimeZone timezone;
        if(isDST) {
            timezone = TimeZone.getTimeZone("EDT");
        } else {
            timezone = TimeZone.getTimeZone("EST");
        }

        DateTime time = new DateTime(millis).toDateTime(DateTimeZone.forTimeZone(timezone));

        return formatter.print(time);
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
