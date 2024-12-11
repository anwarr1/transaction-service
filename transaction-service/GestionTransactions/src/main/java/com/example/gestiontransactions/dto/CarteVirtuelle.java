package com.example.gestiontransactions.dto;

import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarteVirtuelle {


    private int numeroCarte;
    private Date dateExpiration;
    private BigDecimal solde;
    private int cvv;


}
