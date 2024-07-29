package com.API_SupportCustommer.API.controllers;

import com.API_SupportCustommer.API.dto.SupportDto;
import com.API_SupportCustommer.API.model.SupportModel;
import com.API_SupportCustommer.API.repository.SupportRepository;
import com.API_SupportCustommer.API.service.SupportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/support")
public class OpenSupportController {
    private final SupportRepository supportRepository;
    private final SupportService supportService;


    @PostMapping("/create")
    public ResponseEntity createSupport(@RequestBody SupportDto supportDto, SupportModel supportModel2){
        Optional<SupportModel> supportModel = this.supportRepository.findByTicket(supportModel2.getTicket());
        if (supportModel.isEmpty()){
            SupportModel newSupport = new SupportModel();
            newSupport.setDateSupport(supportDto.date());
            newSupport.setTitle(supportDto.title());
            newSupport.setContent(supportDto.content());
            newSupport.setTypeOfProblem(supportDto.type());
            newSupport.setTicket(supportService.createTicket());
            this.supportRepository.save(newSupport);
            return ResponseEntity.ok("Support Created");
        }
        return ResponseEntity.badRequest().build();
    }
}
