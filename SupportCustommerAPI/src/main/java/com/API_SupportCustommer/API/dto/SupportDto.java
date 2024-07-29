package com.API_SupportCustommer.API.dto;

import java.util.Date;
public record SupportDto(String title, String type, String content, Date date, String ticket) {
}
