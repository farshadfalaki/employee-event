package com.takeaway.challenge.event;

import com.takeaway.challenge.model.Outbox;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Service
@Slf4j
public class EventPublisherKafkaTemplateImpl implements EventPublisher {
    @Value(value = "${kafka.employeeEvent.topic.name}")
    private String topicName ;
    public EventPublisherKafkaTemplateImpl(KafkaTemplate<String, Outbox> kafkaTemplate, ProducerSenderCallbackImpl callback) {
        this.kafkaTemplate = kafkaTemplate;
        this.callback = callback;
    }

    private final KafkaTemplate<String, Outbox> kafkaTemplate;
    private final ProducerSenderCallbackImpl callback;

    @Override
    public void publishEvent(Outbox outbox) {
        ListenableFuture<SendResult<String, Outbox>> future = kafkaTemplate.send(topicName, outbox.getId(), outbox);
        future.addCallback(callback);
    }
}
