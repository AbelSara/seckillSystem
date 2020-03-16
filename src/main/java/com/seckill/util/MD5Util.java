package com.seckill.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by Albert on 2020/3/9.
 */
public class MD5Util {
    private static String salt = "1a2b3c4d";

    private static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    private static String inputPassToFormPass(String src) {
        int mid = salt.length() / 2;
        src = salt.substring(0, mid) + src + salt.substring(mid, salt.length());
        return md5(src);
    }

    public static String formPassToDBPass(String src, String dbSalt) {
        int mid = dbSalt.length() / 2;
        src = dbSalt.substring(0, mid) + src + dbSalt.substring(mid, dbSalt.length());
        return md5(src);
    }

    public static String inputPassToDBPass(String src, String dbSalt) {
        src = inputPassToFormPass(src);
        System.out.println(src);
        src = formPassToDBPass(src, dbSalt);
        System.out.println(src);
        return src;
    }
}
