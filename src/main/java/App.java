import java.math.BigInteger;
import org.apache.commons.codec.binary.Hex;
import service.CryptoService;
import service.HellmanService;
import service.UtilService;

/**
 * @author João Vitor Brasil
 */
public class App {

    public static void main(String[] args) {

        var crypto = new CryptoService();
        var hellmann = new HellmanService();
        var utils = new UtilService();

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

        var randomA = hellmann.generateRangeRandomNumber(pDecimal);

        var A = hellmann.calculate(randomA, gDecimal, pDecimal);

        var V = hellmann.calculate(randomA, BDecimal, pDecimal);

        var key = utils.getKeyFrom(V);

        var deciphered = crypto.decipher(key, avelinosMessage);

        var reverseDeciphered = utils.reverseMessage(deciphered);

        var reversedCiphered = crypto.cipher(key, reverseDeciphered);

        utils.printServices(A, deciphered, reverseDeciphered, reversedCiphered);
    }

}
