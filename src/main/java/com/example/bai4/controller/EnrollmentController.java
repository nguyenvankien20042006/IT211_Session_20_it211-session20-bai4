package com.example.bai4.controller;

import com.example.bai4.model.dto.response.EnrollmentSummary;
import com.example.bai4.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/elearning/study")
@RequiredArgsConstructor
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    @GetMapping("/my-progress")
    public ResponseEntity<EnrollmentSummary> enrollmentSummary() {
        return new ResponseEntity<>(enrollmentService.enrollmentSummary(), HttpStatus.OK);
    }

    @PostMapping("/{courseId}/claim-certificate")
    public ResponseEntity<String> getCertificate(@PathVariable Long courseId) {
        return new ResponseEntity<>(enrollmentService.getCertificate(courseId), HttpStatus.OK);
    }
}
