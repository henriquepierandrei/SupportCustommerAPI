package com.API_SupportCustommer.API.controllers;

import com.API_SupportCustommer.API.model.SupportModel;
import com.API_SupportCustommer.API.repository.SupportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final SupportRepository supportRepository;

    @GetMapping("/supports")
    public ResponseEntity<List<SupportModel>> getAllSupports(){
        List<SupportModel> supportModelList = this.supportRepository.findAll();
        return ResponseEntity.ok(supportModelList);
    }
}
