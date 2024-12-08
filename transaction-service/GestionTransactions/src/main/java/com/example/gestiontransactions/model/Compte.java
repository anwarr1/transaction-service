package com.example.gestiontransactions.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data

public class Compte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public Double solde;
    public String devise;

    public Compte(long l, double v) {
    }

    public Compte() {

    }

    public Double getSolde() {
        return solde;
    }

    public void setSolde(Double solde) {
        this.solde = solde;
    }

    @Column(name = "user_id")
    private Integer idUser; // Référence à l'utilisateur

    @OneToMany(mappedBy = "compte")
    private List<PaiementFacture> paiements; // Paiements associés à ce compte

    @OneToMany(mappedBy = "expediteur")
    private List<Virement> virementsExpedies; // Liste des virements effectués par ce compte

    @OneToMany(mappedBy = "destinataire")
    private List<Virement> virementsRecus; // Liste des virements reçus par ce compte
}