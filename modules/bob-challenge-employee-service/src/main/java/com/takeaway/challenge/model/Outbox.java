package com.takeaway.challenge.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.Instant;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Outbox {
    @Id
    @GeneratedValue(generator = "sys-uuid")
    @GenericGenerator(name = "sys-uuid",strategy = "uuid2")
    private String id;
    @Column(nullable = false)
    private String employeeId;
    @Column(nullable = false)
    private String eventType;
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Instant time;
    @JsonIgnore
    private Boolean published;
}
