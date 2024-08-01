package com.API_SupportCustommer.API.controllers;

import ch.qos.logback.core.pattern.util.RegularEscapeUtil;
import com.API_SupportCustommer.API.enuns.StatusEnum;
import com.API_SupportCustommer.API.enuns.TypeProblemEnum;
import com.API_SupportCustommer.API.model.SupportModel;
import com.API_SupportCustommer.API.model.UserModel;
import com.API_SupportCustommer.API.repository.SupportRepository;
import com.API_SupportCustommer.API.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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


    // Listed supports by type problem
    @GetMapping("/supports/problem/{problem}")
    public ResponseEntity<?> getByProblem(@PathVariable(value = "problem") String problem) {
        TypeProblemEnum typeProblemEnum;
        try {
            typeProblemEnum = TypeProblemEnum.valueOf(problem.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problem + " isn't a valid problem type.");
        }

        List<SupportModel> supportModelList = this.supportRepository.findByTypeProblemEnum(typeProblemEnum);
        if (!supportModelList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FOUND).body(supportModelList);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No supports found for the problem type: " + typeProblemEnum);
    }


    // Report about supports
    @GetMapping("report/supports")
    public ResponseEntity<String> getReportAboutTheSupports() {
        // Inicializa os contadores
        Map<String, Integer> statusCounts = new HashMap<>();
        Map<String, Integer> typeCounts = new HashMap<>();

        // Define todos os status e tipos possíveis
        List<String> statuses = Arrays.asList("UNREAD", "INRESOLUTION", "UNRESOLVED", "RESOLVED");
        List<String> types = Arrays.asList("TECHNICAL_ISSUES", "ACCOUNT_AND_ACCESS_PROBLEMS", "PRODUCT_AND_SERVICE_ISSUES",
                "BILLING_AND_PAYMENT_PROBLEMS", "CUSTOMER_SERVICE_ISSUES", "USABILITY_PROBLEMS",
                "SECURITY_ISSUES", "FUNCTIONALITY_PROBLEMS");

        // Inicializa os contadores para cada status e tipo
        for (String status : statuses) {
            statusCounts.put(status, 0);
        }
        for (String type : types) {
            typeCounts.put(type, 0);
        }

        // Conta os suportes
        List<SupportModel> supportModelList = this.supportRepository.findAll();

        for (SupportModel supportModel : supportModelList) {
            String status = supportModel.getStatus().toString();
            String type = supportModel.getTypeProblemEnum().toString();

            // Incrementa o contador para o status se ele existir
            if (statusCounts.containsKey(status)) {
                statusCounts.put(status, statusCounts.get(status) + 1);
            }

            // Incrementa o contador para o tipo se ele existir
            if (typeCounts.containsKey(type)) {
                typeCounts.put(type, typeCounts.get(type) + 1);
            }
        }

        // Gera o relatório
        StringBuilder report = new StringBuilder();
        report.append("Quantity Supports: ").append(supportModelList.size()).append("\n");

        for (String status : statuses) {
            report.append("Quantity Supports ").append(status).append(": ").append(statusCounts.get(status)).append("\n");
        }

        for (String type : types) {
            report.append("Quantity Problem ").append(type).append(": ").append(typeCounts.get(type)).append("\n");
        }

        return ResponseEntity.ok(report.toString());
    }



    // View all type of problems
    @GetMapping("/supports/problem/view")
    public ResponseEntity getAllProblems(){
        return ResponseEntity.status(HttpStatus.FOUND).body(TypeProblemEnum.values());
    }




}
