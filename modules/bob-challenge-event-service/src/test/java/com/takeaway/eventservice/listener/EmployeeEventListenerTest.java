package com.takeaway.eventservice.listener;

import com.takeaway.eventservice.constants.EventType;
import com.takeaway.eventservice.model.Event;
import com.takeaway.eventservice.service.EventService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Instant;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeEventListenerTest {
    @Mock
    EventService eventService;
    @InjectMocks
    EmployeeEventListener employeeEventListener;
    @Test
    public void listen_withComingEvent_shouldCreateIt(){
        //given
        String sampleEmployeeId = "123";
        String sampleEventId = "1000";
        Event event = new Event(sampleEventId,sampleEmployeeId, EventType.CREATE_EVENT, Instant.now());
        //when
        employeeEventListener.listen(event);
        //then
        verify(eventService).create(event);

    }
}
