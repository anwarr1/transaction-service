package com.example.gestiontransactions.model;

import com.example.gestiontransactions.model.Transaction;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class PaiementFacture extends Transaction {

    private String fournisseur;
    private String referenceFacture;

    @ManyToOne
    @JoinColumn(name = "compte_id")
    private Compte compte; // Lié au compte de l'utilisateur

    // Méthode pour traiter le paiement de la facture
    public void traiterPaiement() {
        // Logique pour traiter le paiement
    }

    // Getters and Setters
}
