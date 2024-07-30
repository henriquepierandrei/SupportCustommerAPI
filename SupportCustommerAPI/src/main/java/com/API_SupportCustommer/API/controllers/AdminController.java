package com.API_SupportCustommer.API.controllers;

import com.API_SupportCustommer.API.enuns.StatusEnum;
import com.API_SupportCustommer.API.model.SupportModel;
import com.API_SupportCustommer.API.model.UserModel;
import com.API_SupportCustommer.API.repository.SupportRepository;
import com.API_SupportCustommer.API.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final SupportRepository supportRepository;
    private final UserRepository userRepository;

    // List all Users
    @GetMapping("/users")
    public ResponseEntity<List<UserModel>> getAllUsers(){
        List<UserModel> usersModelList = this.userRepository.findAll();
        return ResponseEntity.ok(usersModelList);
    }

    // List All Supports
    @GetMapping("/supports")
    public ResponseEntity<List<SupportModel>> getAllSupports(){
        List<SupportModel> supportModelList = this.supportRepository.findAll();
        return ResponseEntity.ok(supportModelList);
    }

    // list supports by status
    @GetMapping("/support/{status}")
    public ResponseEntity getSupportByStatus

    // View support by ticket
    @GetMapping("/supports/{ticket}")
    public ResponseEntity gettSupportByTicket(@PathVariable(value = "ticket") String ticket){
        Optional<SupportModel> supportModelupport = this.supportRepository.findByTicket(ticket);
        if (!supportModelupport.isEmpty()){
            SupportModel newSupport =  supportModelupport.get();
            newSupport.setStatus(StatusEnum.IN_RESOLUCAO);
            supportRepository.save(newSupport);
            return ResponseEntity.status(HttpStatus.FOUND).body(supportModelupport);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Support Isn't exists!");
    }


    // Update status
    @PutMapping("/supports/{ticket}/{status}")
    public ResponseEntity<String> updateSupport(@PathVariable(value = "ticket") String ticket,
                                                @PathVariable(value = "status") StatusEnum statusEnum) {
        Optional<SupportModel> supportModelOpt = this.supportRepository.findByTicket(ticket);

        if (supportModelOpt.isPresent()) {
            SupportModel supportModel = supportModelOpt.get();
            supportModel.setStatus(statusEnum);
            supportRepository.save(supportModel);
            return ResponseEntity.ok("Support updated successfully!");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Support doesn't exist!");
    }


}
