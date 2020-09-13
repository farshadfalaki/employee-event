package com.takeaway.eventservice.listener;

import com.takeaway.eventservice.model.Event;
import com.takeaway.eventservice.service.EventService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class EmployeeEventListener {
    private final EventService eventService;

    @KafkaListener(topics = "${kafka.employeeEvent.topic.name}", groupId = "cg1",containerFactory = "kafkaListenerContainerFactory")
    public void listen(Event event) {
        log.info("Received Event : {}" , event);
        eventService.create(event);
    }
}
