package com.API_SupportCustommer.API.controllers;

import com.API_SupportCustommer.API.dto.SupportDto;
import com.API_SupportCustommer.API.model.SupportModel;
import com.API_SupportCustommer.API.repository.SupportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/support")
public class OpenSupportController {
    private final SupportRepository supportRepository;


    @PostMapping("/create")
    public ResponseEntity<SupportModel> createSupport(@RequestBody SupportDto supportDto){
        
    }
}
