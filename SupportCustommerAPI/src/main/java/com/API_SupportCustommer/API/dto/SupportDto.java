package com.API_SupportCustommer.API.dto;

import com.API_SupportCustommer.API.enuns.StatusEnum;
import com.API_SupportCustommer.API.enuns.TypeProblemEnum;

public record SupportDto(String title, StatusEnum status, String content, TypeProblemEnum type) {
}
