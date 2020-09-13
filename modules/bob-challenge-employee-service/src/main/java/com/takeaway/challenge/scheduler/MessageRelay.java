package com.takeaway.challenge.scheduler;

import com.takeaway.challenge.event.EventPublisher;
import com.takeaway.challenge.service.OutboxService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class MessageRelay {
    private final OutboxService outboxService;
    private final EventPublisher eventPublisher;

    @Scheduled(fixedRateString = "${outbox.scheduler.fixRate}")
    public void publishMessage(){
        log.info("Going to fetch and send un-publish outbox message...");
        outboxService.getUnpublishedEvents().forEach(eventPublisher::publishEvent);
    }
}
