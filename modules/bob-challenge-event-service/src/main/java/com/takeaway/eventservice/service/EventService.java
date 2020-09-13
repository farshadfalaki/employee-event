package com.takeaway.eventservice.service;

import com.takeaway.eventservice.model.Event;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EventService {
    List<Event> getEmployeeEvents(String employeeId);
    Event create(Event event);

    @Transactional
    void deleteAll();
}
