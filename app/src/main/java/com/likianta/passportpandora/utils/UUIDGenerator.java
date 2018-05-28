package com.likianta.passportpandora.utils;

import java.util.UUID;

/**
 * Created by Likianta_Dodoora on 2018/5/1 0001.
 */

public class UUIDGenerator {
    // https://blog.csdn.net/carefree31441/article/details/3998553
    
    public UUIDGenerator() {
    }
    
    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        /*
        此时生成的UUID格式为“4cdb1040-657a-4847-b266-7e31d9e2c3d9”
        下面我们可以将里面的连接符删掉
        */
        return str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str
                .substring(19, 23) + str.substring(24);
    }
    
    // 获得指定数量的UUID
    public static String[] getUUID(int number) {
        if (number < 1) {
            return null;
        }
        String[] ss = new String[number];
        for (int i = 0; i < number; i++) {
            ss[i] = getUUID();
        }
        return ss;
    }
}