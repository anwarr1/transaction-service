package com.example.gestiontransactions.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Virement extends Transaction {

    @ManyToOne
    @JoinColumn(name = "compte_expediteur_id")
    private Compte expediteur; // Lien avec le compte de l'expéditeur

    @ManyToOne
    @JoinColumn(name = "compte_destinataire_id")
    private Compte destinataire; // Lien avec le compte du destinataire

    // Logique métier pour effectuer le virement
    public void effectuerVirement() {
        // Exemple de logique pour transférer l'argent du compte expéditeur au compte destinataire
        this.expediteur.setSolde(this.expediteur.getSolde() - this.getMontant());
        this.destinataire.setSolde(this.destinataire.getSolde() + this.getMontant());
    }
}
