package com.pcalouche.awtf_core;

import org.springframework.stereotype.Component;

@Component("MyImpl1")
public class MyImpl1 implements MyInterface {
    @Override
    public String getMyString(String myString) {
        return "My Impl1 " + myString;
    }
}
