package com.example.util;

import java.util.UUID;

/**
 * Created by think on 2020/4/4.
 */
public class UuidUtil {

    public static String getUUID(){
        String uuid = UUID.randomUUID().toString();
        //去掉“-”符号
        return uuid.replaceAll("-", "");
    }
}
