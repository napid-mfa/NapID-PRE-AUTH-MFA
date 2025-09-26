package com.example.napid.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class ApiResponseDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String statusCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String tagerIdentifier;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean isSuccess;
    private String message;

    public ApiResponseDTO(String message, String statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public ApiResponseDTO() {

    }

    public ApiResponseDTO(String message, String tagerIdentifier, String statusCode) {
        this.message = message;
        this.tagerIdentifier = tagerIdentifier;
        this.statusCode = statusCode;
    }

    public ApiResponseDTO(String message, Boolean isSuccess) {
        this.message = message;
        this.isSuccess = isSuccess;
    }
}
