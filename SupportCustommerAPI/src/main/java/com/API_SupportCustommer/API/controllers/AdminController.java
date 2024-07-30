package com.API_SupportCustommer.API.controllers;

import ch.qos.logback.core.pattern.util.RegularEscapeUtil;
import com.API_SupportCustommer.API.enuns.StatusEnum;
import com.API_SupportCustommer.API.model.SupportModel;
import com.API_SupportCustommer.API.model.UserModel;
import com.API_SupportCustommer.API.repository.SupportRepository;
import com.API_SupportCustommer.API.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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


    // View User by id
    @GetMapping("/users/{id}")
    public ResponseEntity getUserById(@PathVariable(value = "id") long id){
        Optional<UserModel> getUser = this.userRepository.findById(id);
        if (getUser.isPresent()){
            return ResponseEntity.ok(getUser.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID:"+id+" Isn't exists!");
    }


    // List All Supports
    @GetMapping("/supports")
    public ResponseEntity<List<SupportModel>> getAllSupports(){
        List<SupportModel> supportModelList = this.supportRepository.findAll();
        return ResponseEntity.ok(supportModelList);
    }

    // list supports by status
    @GetMapping("/supports/status/{status}")
    public ResponseEntity getSupportByStatus(@PathVariable(value = "status") StatusEnum statusEnum){
        List<SupportModel> listSupport = this.supportRepository.findByStatus(statusEnum);
        if (listSupport.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Isn't exists!");
        }
        return ResponseEntity.ok(listSupport);

    }

    // View support by ticket
    @GetMapping("/supports/ticket/{ticket}")
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


    // Update status by ticket
    @PutMapping("/supports/ticket/{ticket}/status/{status}/update")
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

    // List supports by date
    @GetMapping("/supports/date/{date}")
    public ResponseEntity getByTimeSupport(@PathVariable(value = "date") String date) {
        List<SupportModel> listSupportModel = new ArrayList<>();
        List<SupportModel> allSupports = this.supportRepository.findAll();

        for (SupportModel supportModel : allSupports) {
            if (supportModel.getDateSupport().toString().contains(date)) {
                listSupportModel.add(supportModel);
            }
        }

        if (!listSupportModel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FOUND).body(listSupportModel);
        }
        return ResponseEntity.notFound().build();

    }


    // Report about the supports
    @GetMapping("report/supports")
    public ResponseEntity getReportAboutTheSupports(){
        int quantityAllSupports = 0;
        int quantityUNREAD = 0;
        int quantityINRESOLUTION = 0;
        int quantityUNRESOLVED = 0;
        int quantityRESOLVED = 0;

        List<SupportModel> supportModelList = this.supportRepository.findAll();
        for (SupportModel supportModel : supportModelList){
            if (supportModel.getStatus().toString().contains("UNREAD")){quantityUNREAD++;}
            if (supportModel.getStatus().toString().contains("INRESOLUCAO")){quantityINRESOLUTION++;}
            if (supportModel.getStatus().toString().contains("UNRESOLVED")){quantityUNRESOLVED++;}
            if (supportModel.getStatus().toString().contains("RESOLVED")){quantityRESOLVED++;}
            quantityAllSupports++;
        }
        return ResponseEntity.ok("Quantity Supports: "+quantityAllSupports+"\nQuantity Supports UNREAD: "+quantityUNREAD+"\n" +
                "Quantity Supports INRESOLUTION: "+quantityINRESOLUTION+"\nQuantity Supports UNRESOLVED: "+quantityUNRESOLVED+"\n" +
                "Quantity Supports RESOLVED: "+quantityRESOLVED);
    }




}
