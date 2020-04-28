package com.example.util;

import java.util.UUID;

/**
 * @Title: com.example.util
 * @Description:
 * @author: zhchx
 * @date 2020/4/28 14:24
 */
public class UuidUtil {
    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
