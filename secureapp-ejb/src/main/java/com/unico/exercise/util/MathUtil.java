package com.unico.exercise.util;

import java.math.BigInteger;

/**
 * Created by Alireza on 3/18/2017.
 */
public class MathUtil {

    public static int calculateGCD(int a, int b){
        BigInteger b1 = BigInteger.valueOf(a);
        BigInteger b2 = BigInteger.valueOf(b);
        BigInteger gcd = b1.gcd(b2);
        return gcd.intValue();
    }
}
