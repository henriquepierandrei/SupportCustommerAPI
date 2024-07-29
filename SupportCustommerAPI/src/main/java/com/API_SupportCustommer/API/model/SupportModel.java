package com.API_SupportCustommer.API.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UUID;

import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class SupportModel {
    @UUID
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;



    private String title;

    private String content;

    private String typeOfProblem;

    private Date dateSupport;


}
