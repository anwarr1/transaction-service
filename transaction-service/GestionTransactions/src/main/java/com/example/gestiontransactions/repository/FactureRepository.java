package com.example.gestiontransactions.repository;

import com.example.gestiontransactions.model.Facture;
import com.example.gestiontransactions.enums.StatutFacture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FactureRepository extends JpaRepository<Facture, Long> {


}
