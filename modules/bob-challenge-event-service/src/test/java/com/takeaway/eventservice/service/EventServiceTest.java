package com.takeaway.eventservice.service;

import com.takeaway.eventservice.constants.EventType;
import com.takeaway.eventservice.model.Event;
import com.takeaway.eventservice.repository.EventRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@RunWith(MockitoJUnitRunner.class)
public class EventServiceTest {
    @Mock
    EventRepository eventRepository;
    @InjectMocks
    EventServiceImpl eventService;
    @Test
    public void getEmployeeEvents() {
        //given
        String sampleEmployeeId = "123";
        Event event1 = new Event("1",sampleEmployeeId, EventType.CREATE_EVENT , Instant.now());
        Event event2 = new Event("2",sampleEmployeeId, EventType.UPDATE_EVENT , Instant.now());
        List<Event> expectedEventList = Arrays.asList(event1,event2);
        when(eventRepository.findByEmployeeIdOrderByTimeAsc(sampleEmployeeId)).thenReturn(expectedEventList);
        //when
        List<Event> actualEventList = eventService.getEmployeeEvents(sampleEmployeeId);
        //then
        assertEquals(expectedEventList,actualEventList);
        verify(eventRepository).findByEmployeeIdOrderByTimeAsc(sampleEmployeeId);
    }

    @Test
    public void create() {
        //given
        String sampleEmployeeId = "123";
        Event event = new Event("1",sampleEmployeeId, EventType.CREATE_EVENT , Instant.now());
        when(eventRepository.save(event)).thenReturn(event);
        //when
        Event actualEvent = eventService.create(event);
        //then
        assertEquals(event,actualEvent);
        verify(eventRepository).save(event);
    }
}