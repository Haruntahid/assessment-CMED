package com.cmed.health.model.mapper;

import com.cmed.health.model.dtos.PrescriptionDto;
import com.cmed.health.model.entity.Prescription;
import org.springframework.stereotype.Component;

@Component
public class PrescriptionMapper {

    public Prescription map(PrescriptionDto dto){
        Prescription entity = new Prescription();
        entity.setPrescriptionDate(dto.getPrescriptionDate());
        entity.setName(dto.getName());
        entity.setAge(dto.getAge());
        entity.setGender(dto.getGender());
        entity.setDiagnosis(dto.getDiagnosis());
        entity.setMedicines(dto.getMedicines());
        entity.setNextVisitDate(dto.getNextVisitDate());
        return entity;
    }


}
