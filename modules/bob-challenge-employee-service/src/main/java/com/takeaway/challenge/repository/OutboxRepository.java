package com.takeaway.challenge.repository;

import com.takeaway.challenge.model.Outbox;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OutboxRepository extends JpaRepository<Outbox,String> {
    List<Outbox> findByPublishedOrderByTime(Boolean isPublished);
}
