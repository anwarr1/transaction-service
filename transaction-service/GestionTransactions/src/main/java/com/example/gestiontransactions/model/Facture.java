package com.example.gestiontransactions.model;

import com.example.gestiontransactions.enums.StatutFacture;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Facture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fournisseurId;
    private Double montant;
    private Date dateLimite;

    @Enumerated(EnumType.STRING)
    private StatutFacture statut;

    // Méthodes
    public void genererFactureRecurrente() {
        // Logique pour générer une facture récurrente
    }

    public void marquerPayee() {
        // Logique pour marquer la facture comme payée
    }

    // Getters and Setters
}
