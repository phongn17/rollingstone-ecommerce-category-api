package com.rollingstone.rollingstoneapi.listeners;

import com.rollingstone.rollingstoneapi.events.CategoryEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class CategoryEventListener {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @EventListener
    public void onApplicationEvent(CategoryEvent categoryEvent) {
        logger.info("Received Category Event:" + categoryEvent.getEventType());
        logger.info("Received Category from Category Event:" + categoryEvent.getCategory().toString());
    }
}
