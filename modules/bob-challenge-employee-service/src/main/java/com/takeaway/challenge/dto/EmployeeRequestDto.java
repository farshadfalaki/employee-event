package com.takeaway.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;

@Data
@Builder
@AllArgsConstructor
public class EmployeeRequestDto {
    @NotNull
    @Email
    private String email;
    @NotNull
    @NotEmpty
    private String fullName;
    @Pattern(regexp = "^[0-9]{4}-(1[0-2]|0[1-9])-(3[01]|[12][0-9]|0[1-9])$")
    private String birthday;
    @Positive
    @NotNull
    private Long depId;

}
