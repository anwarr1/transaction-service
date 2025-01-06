package com.example.gestiontransactions.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class PaiementFacture extends Transaction {


    @OneToOne
    @JoinColumn(name = "Facture_id")
    private Facture facture;

    @ManyToOne
    @JoinColumn(name = "compte_id")
    //@JsonBackReference
    private Compte compte;

}
