package com.example.napid.controller;

import com.example.napid.dto.*;
import com.example.napid.entity.ApplicationDetailsEntity;
import com.example.napid.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @PostMapping("/v1/application")
    public ResponseEntity<?> createApplication(@RequestBody ApplicationRequestDTO applicationDetailsEntity) {
        return applicationService.createApplication(applicationDetailsEntity);
    }

    @GetMapping("/v1/application")
    public List<ApplicationResponseDTO> getAllApplicationDetails() {
        return applicationService.getAllApplicationDetails();
    }

    @GetMapping("/v1/application/checkstatus/{digitalId}")
    public IsEnabledResponseDTO checkStatusIsEnabled(@PathVariable String digitalId) {
        return applicationService.checkStatusIsEnabled(digitalId);
    }

    @PutMapping("/v1/application")
    public ResponseEntity<?> updateIsEnabledField(@RequestBody UpdateIsEnabledRequestDTO updateIsEnabledRequestDTO) {
        return applicationService.updateIsEnabledField(updateIsEnabledRequestDTO);
    }


}
