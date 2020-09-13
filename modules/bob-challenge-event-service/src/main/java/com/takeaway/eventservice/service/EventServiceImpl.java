package com.takeaway.eventservice.service;

import com.takeaway.eventservice.model.Event;
import com.takeaway.eventservice.repository.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    @Override
    public List<Event> getEmployeeEvents(String employeeId) {
        return eventRepository.findByEmployeeIdOrderByTimeAsc(employeeId);
    }

    @Override
    @Transactional
    public Event create(Event event) {
        Event event1 = eventRepository.save(event);
        System.out.println(event1);
        return event1;
    }

    @Override
    @Transactional
    public void deleteAll() {
        eventRepository.deleteAll();
    }
}
