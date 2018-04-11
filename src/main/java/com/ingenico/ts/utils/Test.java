package com.ingenico.ts.utils;

import org.apache.commons.lang.StringUtils;

public class Test {

    public static void main(String[] args) {
        String str = "1.1";

        if(StringUtils.isNumeric(str)){
            System.out.println("Right");
        }
        else{
            System.out.println("Nopes");
        }

    }
}
