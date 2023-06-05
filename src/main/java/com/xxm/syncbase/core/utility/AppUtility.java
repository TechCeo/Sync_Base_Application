package com.xxm.syncbase.core.utility;

import java.util.Random;

public class AppUtility {
    public AppUtility() {
    }

    public static String randomNumber(int len) {
        String AB = "0123456789";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);

        for(int i = 0; i < len; ++i) {
            sb.append("0123456789".charAt(rnd.nextInt("0123456789".length())));
        }

        return sb.toString();
    }

    public static String randomString(int len) {
        String AB = "ABCDEFGHJKLMNPQRSTUVWXYZ123456789123456789";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);

        for(int i = 0; i < len; ++i) {
            sb.append("ABCDEFGHJKLMNPQRSTUVWXYZ123456789123456789".charAt(rnd.nextInt("ABCDEFGHJKLMNPQRSTUVWXYZ123456789123456789".length())));
        }

        return sb.toString();
    }
}