package com.example.napid.dto;

import lombok.Data;

@Data
public class ApplicationRequestDTO {

    private String applicationName;
    private String digitalID;
    private String mobileNo;
    private Boolean isEnabled;
}
