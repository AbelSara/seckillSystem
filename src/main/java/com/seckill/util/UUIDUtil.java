package com.seckill.util;

import java.util.UUID;

/**
 * Created by Albert on 2020/3/9.
 */
public class UUIDUtil {
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
