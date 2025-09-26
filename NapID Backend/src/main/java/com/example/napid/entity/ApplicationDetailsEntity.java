package com.example.napid.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "application_details")
public class ApplicationDetailsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String applicationName;
    private String digitalID;
    private String mobileNo;
    private Boolean isEnabled;

}
