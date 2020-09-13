package com.takeaway.challenge.service;

import com.takeaway.challenge.constants.EventType;
import com.takeaway.challenge.model.Outbox;
import com.takeaway.challenge.repository.OutboxRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.any;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OutboxServiceTest {
    @Mock
    OutboxRepository outboxRepository;
    @InjectMocks
    OutboxServiceImpl outboxService;
    @Test
    public void create_withEmployeeIdAndEventType_shouldReturnUnpublished() {
        //given
        String sampleId = "1000";
        String sampleEmployeeId = "123";
        String sampleEvent = EventType.CREATE_EVENT;
        when(outboxRepository.save(ArgumentMatchers.any())).thenAnswer(invocationOnMock -> {
            Outbox input = invocationOnMock.getArgument(0);
            input.setId(sampleId);
            return invocationOnMock.getArgument(0);
        });
        //when
        Outbox actualOutbox = outboxService.create(sampleEmployeeId,sampleEvent);
        //then
        assertEquals(actualOutbox.getEmployeeId(),sampleEmployeeId);
        assertEquals(actualOutbox.getEventType(),sampleEvent);
        assertFalse(actualOutbox.getPublished());
        assertEquals(actualOutbox.getId(),sampleId);
        assertNotNull(actualOutbox.getTime());
    }

    @Test
    public void getUnpublishedEvents() {
        //given
        String sampleId = "1000";
        String sampleId2 = "1001";
        String sampleEmployeeId = "123";
        String sampleEvent = EventType.CREATE_EVENT;
        String sampleEvent2 = EventType.UPDATE_EVENT;
        Outbox sampleOutbox = new Outbox(sampleId,sampleEmployeeId,sampleEvent,Instant.now(),false);
        Outbox sampleOutbox2 = new Outbox(sampleId2,sampleEmployeeId,sampleEvent2,Instant.now(),false);
        List<Outbox> expectedOutboxList = Arrays.asList(sampleOutbox,sampleOutbox2);
        when(outboxRepository.findByPublishedOrderByTime(false)).thenReturn(expectedOutboxList);
        //when
        List<Outbox> actualOutboxList = outboxService.getUnpublishedEvents();
        //then
        assertEquals(expectedOutboxList,actualOutboxList);
    }

    @Test
    public void markAsPublished() {
        //given
        String sampleId = "1000";
        String sampleEmployeeId = "123";
        String sampleEvent = EventType.CREATE_EVENT;
        Outbox sampleOutbox = new Outbox(sampleId,sampleEmployeeId,sampleEvent,Instant.now(),false);
        when(outboxRepository.save(ArgumentMatchers.any())).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
        //when
        Outbox actualOutbox = outboxService.markAsPublished(sampleOutbox);
        //then
        assertEquals(actualOutbox.getId(),sampleId);
        assertTrue(actualOutbox.getPublished());
    }
}