package com.API_SupportCustommer.API.controllers;

import com.API_SupportCustommer.API.dto.SupportDto;
import com.API_SupportCustommer.API.enuns.StatusEnum;
import com.API_SupportCustommer.API.model.SupportModel;
import com.API_SupportCustommer.API.model.UserModel;
import com.API_SupportCustommer.API.repository.SupportRepository;
import com.API_SupportCustommer.API.service.SupportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/support")
public class OpenSupportController {
    private final SupportRepository supportRepository;
    private final SupportService supportService;


    @PostMapping("/create")
    public ResponseEntity createSupport(@RequestBody SupportDto supportDto, @AuthenticationPrincipal UserModel user) {
        String ticket;
        Optional<SupportModel> existingSupport;

        // Loop para garantir que o ticket gerado seja único
        do {
            ticket = supportService.createTicket();
            existingSupport = supportRepository.findByTicket(ticket);
        } while (existingSupport.isPresent());

        SupportModel newSupport = new SupportModel();
        newSupport.setDateSupport(new Date());
        newSupport.setTitle(supportDto.title());
        newSupport.setContent(supportDto.content());
        newSupport.setDateSupport(new Date());
        newSupport.setStatus(StatusEnum.UNREAD);
        newSupport.setTicket(ticket);

        user.setQuantityTickets(user.getQuantityTickets() + 1);

        supportRepository.save(newSupport);

        return ResponseEntity.ok("Support Created");
    }



}
