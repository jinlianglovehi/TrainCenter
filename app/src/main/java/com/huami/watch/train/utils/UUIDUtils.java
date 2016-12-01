package com.huami.watch.train.utils;

import java.math.BigInteger;
import java.util.Random;

/**
 * Created by jinliang on 16/11/15.
 */

public class UUIDUtils {

    private static  Random random  =new Random();

    public static String generateGUID(){
        return new BigInteger(165, random).toString(36).toUpperCase();
    }

}
