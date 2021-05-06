package com.rollingstone.rollingstoneapi.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

/*
    Spring Boot Actuator would self-create an instance of the MeterRegistry and send it to the java method to resolve
    the dependency. Inside we are creating a Micrometer Counter using the builder patterns. We are giving it a name,
    a description, a few key value pair as tags and then registering it in the Registry that we received.
    These settings are deeply connected how we can collect Spring Boot Actuator metrics and release them to a host
    of monitoring systems through Prometheus, AWS CloudWatch, Azure Monitoring, Dynatrace, Datadog and so on.
 */
@Configuration
public class CategoryMetricsConfiguration {
    @Bean
    public Counter createdCategoryCreationCounter(MeterRegistry registry) {
        return Counter.builder("com.rollingstone.category.created")
                .description("Number of categories created")
                .tags("environment", "production")
                .register(registry);
    }

    @Bean
    public Counter http400ExceptionCounter(MeterRegistry registry) {
        return Counter.builder("com.rollingstone.CategoryController.HTTP400")
                .description("How many HTTP bad request HTTP 400 requests have been received since start ttime of this instance")
                .tags("environment", "production")
                .register(registry);
    }

    @Bean
    public Counter http404ExceptionCounter(MeterRegistry registry) {
        return Counter.builder("com.rollingstone.CategoryController.HTTP404")
                .description("How many HTTP bad request HTTP 404 requests have been received since start ttime of this instance")
                .tags("environment", "production")
                .register(registry);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.setConnectTimeout(Duration.ofMillis(3000))
                .setReadTimeout(Duration.ofMillis(3000))
                .build();
    }
}
