package com.example.gestiontransactions.repository;

import com.example.gestiontransactions.model.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompteRepository extends JpaRepository<Compte, Long> {
    // Custom query methods can be added here if needed
}