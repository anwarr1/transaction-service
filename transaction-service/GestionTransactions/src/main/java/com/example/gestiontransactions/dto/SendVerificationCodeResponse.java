package com.example.gestiontransactions.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class SendVerificationCodeResponse {
    private String phone;
    private String code;

}


