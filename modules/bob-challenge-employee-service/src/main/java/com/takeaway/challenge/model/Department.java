package com.takeaway.challenge.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Department {
    @Id
    @GeneratedValue(generator = "departmentSeq" , strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "departmentSeq" ,sequenceName = "department_seq" , initialValue = 10)
    private Long id;
    private String name;
}
