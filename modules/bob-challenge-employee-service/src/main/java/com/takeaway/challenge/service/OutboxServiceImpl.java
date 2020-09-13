package com.takeaway.challenge.service;

import com.takeaway.challenge.model.Outbox;
import com.takeaway.challenge.repository.OutboxRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
@Service
@AllArgsConstructor
@Slf4j
public class OutboxServiceImpl implements OutboxService{
    private final OutboxRepository outboxRepository;

    @Override
    public Outbox create(String employeeId, String eventType) {
        log.info("create outbox {} {}",employeeId , eventType);
        Outbox outbox = Outbox.builder().eventType(eventType).time(Instant.now()).published(false).employeeId(employeeId).build();
        return outboxRepository.save(outbox);
    }

    @Override
    public List<Outbox> getUnpublishedEvents() {
        return outboxRepository.findByPublishedOrderByTime(false);
    }

    @Override
    @Transactional
    public Outbox markAsPublished(Outbox outbox) {
        log.info("markAsPublished {}" , outbox.getId());
        outbox.setPublished(true);
        return outboxRepository.save(outbox);
    }
}
