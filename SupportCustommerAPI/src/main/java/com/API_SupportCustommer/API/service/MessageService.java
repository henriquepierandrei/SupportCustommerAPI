package com.API_SupportCustommer.API.service;


import com.API_SupportCustommer.API.model.MessageModel;
import com.API_SupportCustommer.API.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;


    public List<MessageModel> getMessages() {
        return messageRepository.findAll();
    }

    public void saveMessage(MessageModel message) {
        messageRepository.save(message);
    }
}
