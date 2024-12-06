package com.example.gestiontransactions.repository;

import com.example.gestiontransactions.model.PaiementFacture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaiementFactureRepository extends JpaRepository<PaiementFacture, Long> {
}
