package com.takeaway.challenge.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Employee {
    @Id
    @GeneratedValue(generator = "sys-uuid")
    @GenericGenerator(name = "sys-uuid",strategy = "uuid2")
    private String id;
    @Column(unique = true)
    private String email;
    @Column(nullable = false)
    private String fullName;
    @Column(nullable = false)
    private Date birthday;
    @Transient
    private Long depId;
    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    private Department department;

}
