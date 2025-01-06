package com.example.gestiontransactions.model;


import com.example.gestiontransactions.enums.TypeTransaction;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.util.Date;
@Data
public class TransactionAnalytics {
    @Enumerated(EnumType.STRING)
    private TypeTransaction typeTransaction;
    private Double montant;
    private Date date;

    public TransactionAnalytics(TypeTransaction typeTransaction, Double montant, Date date) {
        this.typeTransaction = typeTransaction;
        this.montant = montant;
        this.date = date;
    }
}