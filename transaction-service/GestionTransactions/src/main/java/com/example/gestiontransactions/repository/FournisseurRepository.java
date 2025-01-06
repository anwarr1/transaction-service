package com.example.gestiontransactions.repository;

import com.example.gestiontransactions.model.Facture;
import com.example.gestiontransactions.model.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FournisseurRepository extends JpaRepository<Fournisseur, Long> {
}
