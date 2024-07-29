package com.API_SupportCustommer.API.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class SupportService {

    public String createTicket(){
        Random random = new Random();
        String ticket = null;

        for (int i = 0; i < 15; i++){
            int number = random.nextInt(0,9+1);
            String numberString = String.valueOf(number);
            ticket.concat(numberString);
        }
        return ticket.toString();
    }
}
