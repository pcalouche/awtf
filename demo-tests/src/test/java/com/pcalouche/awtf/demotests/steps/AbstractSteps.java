package com.pcalouche.awtf.demotests.steps;

import com.pcalouche.awtf.demotests.Application;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Application.class)
@SpringBootTest
public abstract class AbstractSteps {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
}
