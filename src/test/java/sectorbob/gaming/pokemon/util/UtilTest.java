package sectorbob.gaming.pokemon.util;

import org.junit.Test;

/**
 * Created by ltm688 on 7/25/16.
 */
public class UtilTest {

    @Test
    public void testExpiryMillis() {

        System.out.println(Util.getExpiryTime(System.currentTimeMillis()));

    }
}
