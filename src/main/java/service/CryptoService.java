package service;

import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Hex;

public class CryptoService {

    public String decipher(SecretKeySpec key, String message) {

        try {
            Cipher cipherDecrypt = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipherDecrypt.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(new byte[16], 0, 16));

            byte[] deciphered = cipherDecrypt.doFinal(Hex.decodeHex(message));

            return new String(Arrays.copyOfRange(deciphered, 16, deciphered.length));

        } catch (Exception ex) {
            throw new RuntimeException("Error when decipher: " + ex.getMessage());
        }

    }

    public byte[] cipher(SecretKeySpec key, String message) {

        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            cipher.init(Cipher.ENCRYPT_MODE, key);

            return cipher.doFinal(message.getBytes());

        } catch (Exception ex) {
            throw new RuntimeException("Error when decipher" + ex.getMessage());
        }

    }

}
