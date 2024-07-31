package com.API_SupportCustommer.API.repository;

import com.API_SupportCustommer.API.model.MessageModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<MessageModel, Long> {
}
