package com.takeaway.challenge.service;

import com.takeaway.challenge.model.Outbox;

import java.util.List;

public interface OutboxService {
    Outbox create(String employeeId,String eventType);
    List<Outbox> getUnpublishedEvents();
    Outbox markAsPublished(Outbox outbox);
}