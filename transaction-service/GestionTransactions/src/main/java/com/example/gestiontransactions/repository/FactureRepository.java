package com.example.gestiontransactions.repository;

import com.example.gestiontransactions.model.Facture;
import com.example.gestiontransactions.enums.StatutFacture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FactureRepository extends JpaRepository<Facture, Long> {

    @Query("SELECT f FROM Facture f WHERE f.fournisseur.id = :fournisseurId")
    List<Facture> findByFournisseurId(@Param("fournisseurId") Long fournisseurId);
}
