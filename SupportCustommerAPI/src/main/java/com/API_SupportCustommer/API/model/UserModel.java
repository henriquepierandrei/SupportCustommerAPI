package com.API_SupportCustommer.API.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String username;

    private String email;

    private String password;

    private boolean isAdmin=false;

    private int quantityTickets;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> ticket = new ArrayList<>();


}
