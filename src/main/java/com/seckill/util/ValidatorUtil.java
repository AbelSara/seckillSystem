package com.seckill.util;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Albert on 2020/3/9.
 */
public class ValidatorUtil {
    private static final Pattern mobile_pattern = Pattern.compile("^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$");

    public static boolean isMobile(String src) {
        Matcher matcher = mobile_pattern.matcher(src);
        return matcher.matches();
    }
}
