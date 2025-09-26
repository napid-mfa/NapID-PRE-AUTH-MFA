package com.example.napid.service;

import com.example.napid.dto.*;
import com.example.napid.entity.ApplicationDetailsEntity;
import com.example.napid.exception.CommonException;
import com.example.napid.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.napid.constant.Constant.FAILED;
import static com.example.napid.constant.Constant.SUCCESS;

@Service
public class ApplicationService {


    @Autowired
    private ApplicationRepository applicationRepository;

    public ResponseEntity<?> createApplication(ApplicationRequestDTO applicationRequestDTO) {
        ApplicationDetailsEntity entity = new ApplicationDetailsEntity();
        Optional<ApplicationDetailsEntity> existingApp = applicationRepository.findByApplicationName(applicationRequestDTO.getApplicationName());
        if (existingApp.isPresent()) {
            throw new CommonException("Application name already exists", "FAILED", HttpStatus.EXPECTATION_FAILED);
        }
        entity.setApplicationName(applicationRequestDTO.getApplicationName());
        entity.setDigitalID(applicationRequestDTO.getDigitalID());
        entity.setMobileNo(applicationRequestDTO.getMobileNo());
        entity.setIsEnabled(applicationRequestDTO.getIsEnabled());
        applicationRepository.save(entity);
        return ResponseEntity.ok(new ApiResponseDTO("Application added successfully", SUCCESS));
    }

    public List<ApplicationResponseDTO> getAllApplicationDetails() {
        return applicationRepository.findAll().stream()
                .map(entity -> {
                    ApplicationResponseDTO dto = new ApplicationResponseDTO();
                    dto.setId(entity.getId());
                    dto.setApplicationName(entity.getApplicationName());
                    dto.setDigitalID(entity.getDigitalID());
                    dto.setMobileNo(entity.getMobileNo());
                    dto.setIsEnabled(entity.getIsEnabled());
                    boolean isCard = entity.getApplicationName().contains("Card");
                    dto.setAlias(maskDigitalId(entity.getDigitalID(), isCard));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public String maskDigitalId(String digitalId, boolean isCard) {
        return isCard ? maskCardApplication(digitalId) : maskNonCardApplication(digitalId);
    }

    private String maskCardApplication(String cardNumber) {
        if (cardNumber == null || cardNumber.isEmpty()) {
            throw new CommonException("Card number must not be null or empty", FAILED, HttpStatus.EXPECTATION_FAILED);
        }
        if (!cardNumber.matches("\\d+")) {
            throw new CommonException("Invalid card format", FAILED, HttpStatus.EXPECTATION_FAILED);

        }
        String lastFour = cardNumber.length() >= 4
                ? cardNumber.substring(cardNumber.length() - 4)
                : cardNumber;

        int numAsterisks = 6 - lastFour.length();
        String masked = "*".repeat(Math.max(0, numAsterisks)) + lastFour;
        return masked;
    }

    private String maskEmail(String digitalId) {
        int atIndex = digitalId.indexOf("@");
        String localPart = digitalId.substring(0, atIndex);
        String domainPart = digitalId.substring(atIndex);
        String maskedLocalPart = maskNonCardApplication(localPart);
        return maskedLocalPart + domainPart;
    }


    private String maskNonCardApplication(String digitalId) {
        if (digitalId == null || digitalId.isEmpty()) {
            throw new CommonException("Digital ID must not be null or empty", FAILED, HttpStatus.EXPECTATION_FAILED);
        }

        if (digitalId.contains("@") && digitalId.contains(".")) {
            return maskEmail(digitalId);
        }

        int length = digitalId.length();
        String firstPart;
        String lastPart;

        if (length >= 4) {
            firstPart = digitalId.substring(0, 2);
            lastPart = digitalId.substring(length - 2);
        } else if (length == 3) {
            firstPart = digitalId.substring(0, 2);
            lastPart = digitalId.substring(2);
        } else {
            firstPart = digitalId;
            lastPart = "";
        }

        int totalVisibleLength = firstPart.length() + lastPart.length();
        int maskLength = 8 - totalVisibleLength;

        return firstPart + "*".repeat(maskLength) + lastPart;
    }


    public IsEnabledResponseDTO checkStatusIsEnabled(String digitalId) {
        Optional<ApplicationDetailsEntity> optionalEntity = applicationRepository.findByDigitalID(digitalId);
        if (optionalEntity.isPresent()) {
            Boolean isEnabled = optionalEntity.get().getIsEnabled();
            IsEnabledResponseDTO responseDTO = new IsEnabledResponseDTO();
            responseDTO.setIsEnabled(isEnabled);
            return responseDTO;
        }
        throw new CommonException("Id is invalid", FAILED, HttpStatus.EXPECTATION_FAILED);
    }

    public ResponseEntity<?> updateIsEnabledField(UpdateIsEnabledRequestDTO updateIsEnabledRequestDTO) {
        Optional<ApplicationDetailsEntity> optionalEntity = applicationRepository.findByDigitalID(updateIsEnabledRequestDTO.getId());
        if (optionalEntity.isPresent()) {
            ApplicationDetailsEntity entity = optionalEntity.get();
            entity.setIsEnabled(updateIsEnabledRequestDTO.getIsEnabled());
            applicationRepository.save(entity);
            return ResponseEntity.ok(new ApiResponseDTO("Updation successful", SUCCESS));
        }
        throw new CommonException("Id is invalid", FAILED, HttpStatus.EXPECTATION_FAILED);
    }
}
