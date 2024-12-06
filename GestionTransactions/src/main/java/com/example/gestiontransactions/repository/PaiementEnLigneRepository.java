package com.example.gestiontransactions.repository;

import com.example.gestiontransactions.model.PaiementEnLigne;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaiementEnLigneRepository extends JpaRepository<PaiementEnLigne, Long> {
}
