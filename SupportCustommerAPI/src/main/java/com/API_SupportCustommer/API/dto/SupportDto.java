package com.API_SupportCustommer.API.dto;

import com.API_SupportCustommer.API.enuns.StatusEnum;
import com.API_SupportCustommer.API.enuns.TypeProblemStatus;

import java.util.Date;
public record SupportDto(String title, StatusEnum status, String content, TypeProblemStatus typeProblemStatus) {
}
