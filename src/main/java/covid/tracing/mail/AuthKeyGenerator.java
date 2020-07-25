package covid.tracing.mail;

import java.util.Random;

public class AuthKeyGenerator {

    public static final int MAX_AUTH_KEY_LENGTH = 6;

    public static String generate() {
        Random random = new Random(System.currentTimeMillis());

        int range = (int) Math.pow(10, MAX_AUTH_KEY_LENGTH);
        int trim = (int) Math.pow(10, MAX_AUTH_KEY_LENGTH - 1);
        int result = random.nextInt(range) + trim;

        if (result > range) {
            result = result - trim;
        }

        return String.valueOf(result);
    }
}
