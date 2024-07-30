package com.API_SupportCustommer.API.model;

import com.API_SupportCustommer.API.enuns.StatusEnum;
import com.API_SupportCustommer.API.enuns.TypeProblemStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class SupportModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String title;
    private String ticket;
    private String content;
    private StatusEnum status;
    private Date dateSupport;
    private TypeProblemStatus typeProblemStatus;
}
