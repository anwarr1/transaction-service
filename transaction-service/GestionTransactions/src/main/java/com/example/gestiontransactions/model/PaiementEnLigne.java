package com.example.gestiontransactions.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class PaiementEnLigne extends Transaction {

    private String fournisseur;
    private String referenceTransaction;

    @ManyToOne
    @JoinColumn(name = "compte_id")
    private Compte compte;

    public void effectuerPaiement() {
        // Logique pour effectuer un paiement en ligne
    }

    // Getters and Setters
}
