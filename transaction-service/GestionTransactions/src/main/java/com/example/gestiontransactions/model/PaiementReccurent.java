package com.example.gestiontransactions.model;

import com.example.gestiontransactions.enums.FrequencePaiement;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class PaiementReccurent extends Transaction {

    @ManyToOne
    @JoinColumn(name = "compte_id")
    private Compte compte;

    @Enumerated(EnumType.STRING)
    private FrequencePaiement frequence;

    private Date prochainePaiementDate;

    public void planifier() {
        // Logique pour planifier un paiement récurrent
    }

    public void annuler() {
        // Logique pour annuler le paiement récurrent
    }

    // Getters and Setters
}
