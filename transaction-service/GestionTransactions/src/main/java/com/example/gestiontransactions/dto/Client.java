package com.example.gestiontransactions.dto;

import lombok.Data;

@Data
public class Client {

    private Long id;
    private String lastname;
    private String firstname;
    private String email;
    private String phonenumber;
    private String password;
    private byte[] cinRectoPath;
    private byte[] cinVersoPath;
    private String accountType;
    private boolean firstLogin;
}