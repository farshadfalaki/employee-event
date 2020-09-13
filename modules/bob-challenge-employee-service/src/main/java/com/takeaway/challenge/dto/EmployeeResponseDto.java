package com.takeaway.challenge.dto;

import com.takeaway.challenge.model.Department;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponseDto {
    private String id;
    private String email;
    private String fullName;
    private String birthday;
    private Department department;
}
