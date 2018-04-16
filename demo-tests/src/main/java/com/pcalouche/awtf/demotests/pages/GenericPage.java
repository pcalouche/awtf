package com.pcalouche.awtf.demotests.pages;

import com.pcalouche.awtf.core.pages.PageObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "cucumber-glue")
public class GenericPage extends PageObject {
}
