package com.pcalouche.awtf_core;

import org.springframework.stereotype.Component;

@Component("MyImpl2")
//@Qualifier("MyImpl2")
public class MyImpl2 implements MyInterface {
    @Override
    public String getMyString(String myString) {
        return "My Impl2 " + myString;
    }
}
