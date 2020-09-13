package com.takeaway.challenge.scheduler;

import com.takeaway.challenge.constants.EventType;
import com.takeaway.challenge.event.EventPublisher;
import com.takeaway.challenge.model.Outbox;
import com.takeaway.challenge.service.OutboxService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
@RunWith(MockitoJUnitRunner.class)
public class MessageRelayTest {
    @Mock
    OutboxService outboxService;
    @Mock
    EventPublisher eventPublisher;
    @InjectMocks
    MessageRelay messageRelay;
    @Test
    public void publishMessage() {
        //given
        String sampleId = "1000";
        String sampleId2 = "1001";
        String sampleEmployeeId = "123";
        String sampleEvent = EventType.CREATE_EVENT;
        String sampleEvent2 = EventType.UPDATE_EVENT;
        Outbox sampleOutbox = new Outbox(sampleId,sampleEmployeeId,sampleEvent, Instant.now(),false);
        Outbox sampleOutbox2 = new Outbox(sampleId2,sampleEmployeeId,sampleEvent2,Instant.now(),false);
        List<Outbox> unpublishedOutboxList = Arrays.asList(sampleOutbox,sampleOutbox2);
        when(outboxService.getUnpublishedEvents()).thenReturn(unpublishedOutboxList);
        //when
        messageRelay.publishMessage();
        //then
        verify(outboxService).getUnpublishedEvents();
        verify(eventPublisher).publishEvent(sampleOutbox);
        verify(eventPublisher).publishEvent(sampleOutbox2);
    }
}