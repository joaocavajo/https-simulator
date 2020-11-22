package service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Hex;

public class UtilService {

    public SecretKeySpec getKeyFrom(BigInteger password) {
        byte[] dataBytes = password.toByteArray();

        try {
            MessageDigest algorithm = MessageDigest.getInstance("SHA-256");

            byte[] messageDigest = algorithm.digest(dataBytes);

            return new SecretKeySpec(Arrays.copyOfRange(messageDigest, 0, 16), "AES");

        } catch (NoSuchAlgorithmException e) {
            return null;
        }

    }

    public String reverseMessage(String message) {
        return new StringBuilder(message).reverse().toString();
    }

    public void printServices(BigInteger A, String deciphered, String reverseDeciphered, byte[] reversedCiphered) {
        System.out.println("Message A: " + A.toString(16).toUpperCase());
        System.out.println("\nDecipher message: " + deciphered);
        System.out.println("\nReverse message: " + reverseDeciphered);
        System.out.println("\nReverse message with cipher and hex format: " + Hex.encodeHexString(reversedCiphered, false));
    }
}
