package com.takeaway.challenge.event;

import com.takeaway.challenge.model.Outbox;

public interface EventPublisher {
    void publishEvent(Outbox outbox);
}
