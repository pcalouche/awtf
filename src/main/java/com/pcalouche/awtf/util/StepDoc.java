package com.pcalouche.awtf.util;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface StepDoc {
    String gherkinString();

    String description();
}
