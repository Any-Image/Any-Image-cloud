package com.icloud.controller.utils;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class Code {
    private String [] sub ={"000000","00000","0000","000","00","0",""};

    public String codeGenerator(String tele){
        int hash = tele.hashCode();
        int encryption = 775295;
        long result = hash ^encryption;
        long timeNow = System.currentTimeMillis();
        result ^=timeNow;
        long code = result%1000000;
        code = code < 0 ? -code : code ;
        String codeStr = code + "";
        int len = codeStr.length();
        return sub[len] + codeStr;
    }
}
