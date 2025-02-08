package com.cmed.health.model.dtos;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PrescriptionEditDto {

    @NotNull(message = "Patient name cannot be null")
    @Size(min = 3,max = 40, message="Name must be between 3 to 40 characters")
    private String name;

    @NotNull(message = "Patient age cannot be null")
    @Min(value = 0, message = "Patient age must be at least 0")
    @Max(value = 120, message = "Patient age must be at most 120")
    private int age;

    @NotNull(message = "Gender cannot be null")
    private String gender;
    private String diagnosis;
    private String medicines;

    @Future(message = "Next visit date must be a future date")
    private Date nextVisitDate;
}
