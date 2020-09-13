package com.takeaway.challenge.event;

import com.takeaway.challenge.model.Outbox;
import com.takeaway.challenge.service.OutboxService;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.kafka.support.SendResult;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
@RunWith(MockitoJUnitRunner.class)
public class ProducerSenderCallbackImplTest {
    @Mock
    OutboxService outboxService;
    @InjectMocks
    ProducerSenderCallbackImpl producerSenderCallback;


    @Test
    public void onSuccess() {
        //given
        Outbox outbox = new Outbox();
        ProducerRecord<String, Outbox> precord = new ProducerRecord<>("topic","key",outbox);
        SendResult<String, Outbox> result = new SendResult<>(precord,null);
        //when
        producerSenderCallback.onSuccess(result);
        //then
        verify(outboxService).markAsPublished(outbox);

    }
}