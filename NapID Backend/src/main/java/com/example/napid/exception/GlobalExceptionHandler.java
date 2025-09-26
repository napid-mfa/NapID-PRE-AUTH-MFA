package com.example.napid.exception;



import com.example.napid.dto.ApiResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.Map;




@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        ApiResponseDTO response = new ApiResponseDTO(errors.toString(),"FAILED");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<ApiResponseDTO> handleCommonException(CommonException ex) {
        ApiResponseDTO errorResponse = new ApiResponseDTO(ex.getMessage(), ex.getErrorCode());
        return ResponseEntity.status(ex.getHttpStatus()).body(errorResponse);
    }
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ApiResponseDTO> handleHttpClientErrorException(HttpClientErrorException ex) {
        String message = ex.getResponseBodyAsString();
        String statusCode = ex.getStatusCode().toString();
        ApiResponseDTO errorResponse = new ApiResponseDTO(message, statusCode);
        return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
    }


}
