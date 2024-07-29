package com.API_SupportCustommer.API.repository;

import com.API_SupportCustommer.API.model.SupportModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SupportRepository extends JpaRepository<SupportModel, UUID> {
}
