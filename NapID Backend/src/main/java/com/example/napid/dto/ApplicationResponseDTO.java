package com.example.napid.dto;

import lombok.Data;

@Data
public class ApplicationResponseDTO {

    private int id;
    private String applicationName;
    private String digitalID;
    private String mobileNo;
    private Boolean isEnabled;
    private String alias;
}
