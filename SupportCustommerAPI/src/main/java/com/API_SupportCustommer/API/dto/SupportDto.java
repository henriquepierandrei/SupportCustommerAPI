package com.API_SupportCustommer.API.dto;

import com.API_SupportCustommer.API.enuns.StatusEnum;

import java.util.Date;
public record SupportDto(String title, StatusEnum type, String content) {
}
