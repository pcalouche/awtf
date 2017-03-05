package com.pcalouche.awtf;


import com.pcalouche.awtf.util.StepDoc;
import cucumber.api.java.en.Then;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

public class AnnotationFinder {
    public static void main(String[] args) {
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(true);
        provider.addIncludeFilter(new AssignableTypeFilter(Object.class));

        Set<BeanDefinition> components = provider.findCandidateComponents("com/pcalouche/awtf/stepDefinitions");

        try {
            for (BeanDefinition component : components) {
                System.out.println("Processing " + component.getBeanClassName());
                Class cls = Class.forName(component.getBeanClassName());

                Method[] methods = cls.getMethods();
                for (Method method : methods) {
                    Annotation[] annotations = method.getAnnotations();
                    for (Annotation annotation : annotations) {
                        if (annotation instanceof Then) {
                            Then myAnnotation = (Then) annotation;
                            System.out.println("value: " + myAnnotation.value());
                            StepDoc x = method.getAnnotation(StepDoc.class);
                            if (x == null) {
                                System.out.println("null needs to be added");
                            } else {
                                System.out.println("not null->" + x.description() + " " + x.gherkinString());
                                System.out.println("!!!! " + StepDoc.class.getSimpleName());
                            }
                        }
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Step Class | Gherkin | Example | Description

        // TdStepReportItem
        // Step Class | Gherkin | Example | Description |
    }
}
