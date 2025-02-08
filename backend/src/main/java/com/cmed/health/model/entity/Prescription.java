package com.cmed.health.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date prescriptionDate;
    private String name;
    private int age;
    private String gender;
    private String diagnosis;
    private String medicines;
    private Date nextVisitDate;

}
