package com.example.napid.dto;

import lombok.Data;

@Data
public class UpdateIsEnabledRequestDTO {

    private String id;
    private Boolean isEnabled;
}
