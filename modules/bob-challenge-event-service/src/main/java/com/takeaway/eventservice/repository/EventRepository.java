package com.takeaway.eventservice.repository;

import com.takeaway.eventservice.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends MongoRepository<Event, String> {
    List<Event> findByEmployeeIdOrderByTimeAsc(String employeeId);
}
