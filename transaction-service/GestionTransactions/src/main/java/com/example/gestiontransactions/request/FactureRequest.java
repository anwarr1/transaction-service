package com.example.gestiontransactions.request;

import lombok.Data;

@Data
public class FactureRequest {
    private Long utilisateurId;
    private Double somme;

}