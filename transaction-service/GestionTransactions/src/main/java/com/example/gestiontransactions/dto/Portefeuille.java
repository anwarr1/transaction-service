package com.example.gestiontransactions.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Portefeuille {
    private int id;
    private int utilisateurId;
    private BigDecimal solde;

}
