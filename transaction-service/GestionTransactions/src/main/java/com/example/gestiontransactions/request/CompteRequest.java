package com.example.gestiontransactions.request;

import com.example.gestiontransactions.dto.SMS;
import com.example.gestiontransactions.model.Virement;
import lombok.Data;

@Data
public class CompteRequest {
    private Long utilisateurId;
    private Double somme;
}
