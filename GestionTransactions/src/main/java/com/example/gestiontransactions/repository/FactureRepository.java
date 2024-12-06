package com.example.gestiontransactions.repository;

import com.example.gestiontransactions.model.Facture;
import com.example.gestiontransactions.enums.StatutFacture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FactureRepository extends JpaRepository<Facture, Long> {

    // Vous pouvez ajouter des méthodes spécifiques si nécessaire, par exemple :
    // Trouver les factures en fonction de leur statut
    List<Facture> findByStatut(StatutFacture statut);

    // Trouver les factures d'un fournisseur spécifique
    List<Facture> findByFournisseurId(String fournisseurId);

}
