package com.rollingstone.rollingstoneapi.aspect;

import io.micrometer.core.instrument.Counter;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RestControllerAspect {
    private final Logger logger = LoggerFactory.getLogger("RestControllerAspect");
    @Autowired
    Counter createdCategoryCreationCounter;

    @Before("execution(public * com.rollingstone.rollingstoneapi.spring.controller.*Controller.*(..))")
    public void generalAllMethodAspect() {
        logger.info("All method calls invoke this general aspect method");
    }

    @AfterReturning("execution(public * com.rollingstone.rollingstoneapi.spring.controller.*Controller.createCategory(..))")
    public void getsCalledOnCategorySave() {
        logger.info("This aspect is fired when the createCategory method of the controller is called");
        createdCategoryCreationCounter.increment();
    }
}
