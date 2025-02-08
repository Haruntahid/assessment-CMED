package com.cmed.health.service;

import com.cmed.health.exception.ResourceNotFoundException;
import com.cmed.health.model.dtos.PrescriptionDto;
import com.cmed.health.model.dtos.PrescriptionEditDto;
import com.cmed.health.model.entity.Prescription;
import com.cmed.health.model.mapper.PrescriptionMapper;
import com.cmed.health.repo.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PrescriptionService {

    private final PrescriptionRepository repository;
    private final PrescriptionMapper mapper;
    private final RestTemplate restTemplate;


    public Prescription findById(Long id) {
        Prescription prescription = repository.findById(id).orElse(null);
        if (prescription != null) {
            return prescription;
        }
        throw new ResourceNotFoundException("Prescription not found with id: " + id);
    }

    public void savePrescription(PrescriptionDto dto){
        Prescription prescription = mapper.map(dto);
        repository.save(prescription);
    }

    public void editPrescription(Long id, PrescriptionEditDto dto){
        Prescription existingPrescription  = findById(id);

        existingPrescription.setName(dto.getName());
        existingPrescription.setAge(dto.getAge());
        existingPrescription.setGender(dto.getGender());
        existingPrescription.setDiagnosis(dto.getDiagnosis());
        existingPrescription.setMedicines(dto.getMedicines());
        existingPrescription.setNextVisitDate(dto.getNextVisitDate());

        repository.save(existingPrescription);
    }


    public void deletePrescription(Long id) {
        Prescription prescription  = findById(id);
        repository.delete(prescription);
    }

    public Page<Prescription> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

//    public Page<Prescription> findByDateRange(LocalDate startDate, LocalDate endDate, Pageable pageable) {
//        return repository.findByDateRange(startDate, endDate, pageable);
//    }




    // day wise counter
    public List<Map<String, Object>> getDayWisePrescriptionCount() {
        List<Object[]> rawResults = repository.getDayWisePrescriptionCount();

        return rawResults.stream().map(result -> {
            Map<String, Object> map = new HashMap<>();
            map.put("date", result[0]);
            map.put("count", result[1]);
            return map;
        }).toList();
    }


    //external api
    public Object getDataFromExternalApi() {
        String url = "https://rxnav.nlm.nih.gov/REST/interaction/interaction.json?rxcui=341248";
        ResponseEntity<?> response = restTemplate.getForEntity(url, Object.class);
        return response.getBody();
    }
}
