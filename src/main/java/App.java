import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Hex;

/**
 * @author João Vitor Brasil
 */
public class App {

    public static void main(String[] args) {

        var p = "B10B8F96A080E01DDE92DE5EAE5D54EC52C99FBCFB06A3C6" +
                "9A6A9DCA52D23B616073E28675A23D189838EF1E2EE652C0" +
                "13ECB4AEA906112324975C3CD49B83BFACCBDD7D90C4BD70" +
                "98488E9C219A73724EFFD6FAE5644738FAA31A4FF55BCCC0" +
                "A151AF5F0DC8B4BD45BF37DF365C1A65E68CFDA76D4DA708" +
                "DF1FB2BC2E4A4371";

        var g = "A4D1CBD5C3FD34126765A442EFB99905F8104DD258AC507F" +
                "D6406CFF14266D31266FEA1E5C41564B777E690F5504F213" +
                "160217B4B01B886A5E91547F9E2749F4D7FBD7D3B9A92EE1" +
                "909D0D2263F80A76A6A24C087A091F531DBF0A0169B6A28A" +
                "D662A4D18E73AFA32D779D5918D08BC8858F4DCEF97C2A24" +
                "855E6EEB22B3B2E5";

        var avelinosB = "0D41B01C4480DB879358D05C6B7F3F4E71B53039EBD5AC20" +
                "54342BBCE2A8D120B401A771BA1AB4662D858E3B84E221BB" +
                "1F6D9CDB589D27ED900D006EFBB79B3B75E6EC5E72680369" +
                "8ECE73BB645469C83C84FA2CA031DAA1A49FD2CA5B9830B7" +
                "6AE01DE8604EFDF220FA90EBF845A8A8E9DE3002719DE747" +
                "405C80C2DC8C9370";

        var avelinosMessage = "E43A698F8301CE49F97C49CAFD221FA11C6097955E" +
                "693084E86437F8A5D36A5869C927CA8B347A28731A07D84490C9917A" +
                "253DA1622490C98127268A0A925FEFD4B0D70E768F10CBAC61C4FB18" +
                "581E726C26D932D275B49350106AD7CCB15F4E";

        var pDecimal = new BigInteger(p, 16);

        var gDecimal = new BigInteger(g, 16);

        var BDecimal = new BigInteger(avelinosB, 16);

        var randomA = generateRangeRandomNumber(pDecimal);

        var A = calculateHellman(randomA, gDecimal, pDecimal);

        System.out.println("Message A: " + A.toString(16).toUpperCase());

        var V = calculateHellman(randomA, BDecimal, pDecimal);

        var key = getKeyFrom(V);

        var deciphered = decipher(key, avelinosMessage);

        System.out.println("\nDecipher message: " + deciphered);

        var reverseDeciphered = new StringBuilder(deciphered).reverse().toString();

        System.out.println("\nReverse message: " + reverseDeciphered);

        var reversedCiphered = cipher(reverseDeciphered, key);

        System.out.println("DOne? " + Hex.encodeHexString(reversedCiphered, false));
    }

    private static BigInteger generateRangeRandomNumber(BigInteger p) {
        //return new BigInteger(p.bitLength() - 1, new Random());
        return new BigInteger("25871485258682674135778040149872947127382250037771" +
                "091581995132388642827720994948354016341690190222559039923977380234721" +
                "680272735198905389081051273361625214663954701405755487302622215453064" +
                "705197101160342515568153692132515759809503430994609559815718182148996" +
                "506429985695962580514828604949295904892736634820316");
    }

    private static BigInteger calculateHellman(BigInteger random, BigInteger g, BigInteger p) {
        return g.modPow(random, p);
    }

    private static SecretKeySpec getKeyFrom(BigInteger password) {
        byte[] dataBytes = password.toByteArray();

        try {
            MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
            byte[] messageDigest = algorithm.digest(dataBytes);

            return new SecretKeySpec(Arrays.copyOfRange(messageDigest, 0, 16), "AES");

        } catch (NoSuchAlgorithmException e) {
            return null;
        }

    }

    public static String decipher(SecretKeySpec key, String message) {
        try {
            Cipher cipherDecrypt = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipherDecrypt.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(new byte[16], 0, 16));

            byte[] deciphered = cipherDecrypt.doFinal(Hex.decodeHex(message));

            return new String(Arrays.copyOfRange(deciphered, 16, deciphered.length));
        } catch (Exception ex) {
            throw new RuntimeException("Error when decipher" + ex.getMessage());
        }

    }

    public static byte[] cipher(String message, SecretKeySpec key) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] encrypted = cipher.doFinal(message.getBytes());

            System.out.println("Ciphered reversed message: " + Hex.encodeHexString(encrypted));

            return encrypted;
        } catch (Exception ex) {
            throw new RuntimeException("Error when decipher" + ex.getMessage());
        }
    }
}
