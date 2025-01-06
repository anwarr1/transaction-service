package com.example.gestiontransactions.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    public String Bank;
    public String rib;

    public Compte(long l, double v) {
    }

    public Compte() {

    }



    @Column(name = "user_id")
    private Integer idUser; // Référence à l'utilisateur

    @OneToMany(mappedBy = "compte")
    @JsonIgnore
    private List<PaiementFacture> paiements; // Paiements associés à ce compte

    @OneToMany(mappedBy = "expediteur")
    private List<Virement> virementsExpedies; // Liste des virements effectués par ce compte

    @OneToMany(mappedBy = "destinataire")
    private List<Virement> virementsRecus; // Liste des virements reçus par ce compte
}