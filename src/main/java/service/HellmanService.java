package service;

import java.math.BigInteger;
import java.util.Random;

public class HellmanService {

    public BigInteger calculate(BigInteger random, BigInteger g, BigInteger p) {
        return g.modPow(random, p);
    }

    public BigInteger generateRangeRandomNumber(BigInteger p) {
        return new BigInteger(p.bitLength() - 1, new Random());
       /* To do the homework, communicating with the teacher, the random number was used below:
       return new BigInteger("25871485258682674135778040149872947127382250037771" +
                "091581995132388642827720994948354016341690190222559039923977380234721" +
                "680272735198905389081051273361625214663954701405755487302622215453064" +
                "705197101160342515568153692132515759809503430994609559815718182148996" +
                "506429985695962580514828604949295904892736634820316"); */
    }

}
