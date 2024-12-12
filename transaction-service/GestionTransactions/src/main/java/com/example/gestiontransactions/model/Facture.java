package com.example.gestiontransactions.model;

import com.example.gestiontransactions.enums.StatutFacture;
import com.example.gestiontransactions.enums.TypeService;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Facture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "fournisseur_id")
    private Fournisseur fournisseur;
    private Double montant;
    private Date dateLimite;

    @Enumerated(EnumType.STRING)
    private StatutFacture statut;

    @Enumerated(EnumType.STRING)
    private TypeService type_facture;

}
