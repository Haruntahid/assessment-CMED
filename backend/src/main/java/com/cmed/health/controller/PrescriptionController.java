package com.cmed.health.controller;

import com.cmed.health.model.dtos.PrescriptionDto;
import com.cmed.health.model.dtos.PrescriptionEditDto;
import com.cmed.health.model.entity.Prescription;
import com.cmed.health.response.ApiResponse;
import com.cmed.health.response.ResponseWithData;
import com.cmed.health.service.PrescriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PrescriptionController {

    private final PrescriptionService service;

    @GetMapping("/prescription")
//    public Page<Prescription> getAllPrescriptions(
//            @RequestParam(defaultValue = "1") int page,
//            @RequestParam(defaultValue = "10") int size,
//            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
//            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
//    ) {
//        // If no date range is provided, default to the current month
//        if (startDate == null || endDate == null) {
//            startDate = LocalDate.now().withDayOfMonth(1); // First day of the month
//            endDate = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth()); // Last day of the month
//        }
//
//        Pageable pageable = PageRequest.of(page - 1, size);
//        return service.findByDateRange(startDate, endDate, pageable);
//    }


    public Page<Prescription> getAllPrescriptions(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        Pageable pageable = PageRequest.of(page - 1, size);
        return service.findAll(pageable);
    }



    @PostMapping("/prescriptions")
    public ResponseEntity<ApiResponse> savePrescription(@Valid @RequestBody PrescriptionDto dto){
        service.savePrescription(dto);
        return ApiResponse.success(HttpStatus.CREATED,"Prescription Created");
  }

  @PatchMapping("/prescriptions/{id}")
    public ResponseEntity<ApiResponse> editPrescription(@PathVariable Long id,@Valid @RequestBody PrescriptionEditDto dto){
      service.editPrescription(id,dto);
      return ApiResponse.success(HttpStatus.OK,"Prescription Updated");
  }

  @DeleteMapping("/prescriptions/{id}")
    public ResponseEntity<ApiResponse> deletePrescription(@PathVariable Long id){
      service.deletePrescription(id);
      return ApiResponse.success(HttpStatus.OK,"Prescription Deleted");
  }

  @GetMapping("/report")
  public List<Map<String, Object>> getDayWisePrescriptionCount() {
      return service.getDayWisePrescriptionCount();
  }


  @GetMapping("/prescription/{id}")
    public ResponseEntity<ResponseWithData> getPrescription(@PathVariable Long id){
      service.findById(id);
      return ResponseWithData.successData(HttpStatus.OK,"Prescription Found",service.findById(id));
  }


    @GetMapping("/posts")
    public ResponseEntity<ResponseWithData> getPosts() {
        Object posts = service.getDataFromExternalApi();
        return ResponseWithData.successData(HttpStatus.OK,"Success",posts);
    }


}
