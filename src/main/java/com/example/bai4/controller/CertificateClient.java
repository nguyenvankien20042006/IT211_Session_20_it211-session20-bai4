package com.example.bai4.controller;

import com.example.bai4.model.dto.request.GetCertificateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "certificate-client",
        url = "https://6a237c3e5c610353286aeb4a.mockapi.io/api/v1/get-certificate"
)
public interface CertificateClient {
    @PostMapping("/data")
    String generateCertificate(@RequestBody GetCertificateRequest request);
}
