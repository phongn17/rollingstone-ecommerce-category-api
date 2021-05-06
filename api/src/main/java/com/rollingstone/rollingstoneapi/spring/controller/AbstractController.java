package com.rollingstone.rollingstoneapi.spring.controller;

import com.rollingstone.rollingstoneapi.exceptions.HTTP400Exception;
import com.rollingstone.rollingstoneapi.exceptions.HTTP404Exception;
import com.rollingstone.rollingstoneapi.exceptions.RestAPIExceptionInfo;
import io.micrometer.core.instrument.Counter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;

/*
    Call the setApplicationEventPublisher method during Startup with an instance of the ApplicationEventPublisher class from Spring Boot internal framework.
    That instance will be used later to publish events to be captured by Spring Boot and send the events to our listeners
 */
public abstract class AbstractController implements ApplicationEventPublisherAware {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected ApplicationEventPublisher eventPublisher;
    protected static final String DEFAULT_PAGE_SIZE = "20";
    protected static final String DEFAULT_PAGE_NUMBER = "0";
    @Autowired
    Counter http400ExceptionCounter;
    @Autowired
    Counter http404ExceptionCounter;

    /*
        These are related to a concept called Central Exception Handling - ease of maintenance
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST) // generate HTTP error code / message back to the client
    @ExceptionHandler(HTTP400Exception.class) // telling Spring Boot to call this method central to our abstract class
    public @ResponseBody RestAPIExceptionInfo handleBadRequestException(HTTP400Exception ex, WebRequest request, HttpServletResponse response) {
        logger.info("Received Bad request exception: " + ex.getLocalizedMessage());
        http400ExceptionCounter.increment();

        return new RestAPIExceptionInfo(ex.getLocalizedMessage(), "The Request did not have the correct parameters");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public @ResponseBody RestAPIExceptionInfo handleBadRequestExceptionForJsonBody(HttpMessageNotReadableException ex, WebRequest request, HttpServletResponse response) {
        logger.info("Received Bad request exception: " + ex.getLocalizedMessage());
        http400ExceptionCounter.increment();

        return new RestAPIExceptionInfo(ex.getLocalizedMessage(), "The Request did not have the correct json body");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(HTTP404Exception.class)
    public @ResponseBody RestAPIExceptionInfo handleResourceNotFoundException(HTTP404Exception ex, WebRequest request, HttpServletResponse response) {
        logger.info("Received Resource Not Found exception: " + ex.getLocalizedMessage());
        http404ExceptionCounter.increment();

        return new RestAPIExceptionInfo(ex.getLocalizedMessage(), "The Requested Resource was not found");
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public static <T> T checkResourceFound(final T resource) {
        if (resource == null) {
            throw new HTTP404Exception("Resource not found");
        }

        return resource;
    }
}
