package buaasoft.blog.utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Yirany
 * @version 1.0
 * @since 8/23/2019
 **/
public class HashGenerator {

    public static String sha256(String message) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.reset();
            digest.update(message.getBytes(StandardCharsets.UTF_8));
            return String.format("%064x", new BigInteger(1, digest.digest()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            System.exit(1);

            // shall never get here
            return "";
        }
    }
}