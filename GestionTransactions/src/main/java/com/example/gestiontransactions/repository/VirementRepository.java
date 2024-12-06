package com.example.gestiontransactions.repository;

import com.example.gestiontransactions.model.Virement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VirementRepository extends JpaRepository<Virement, Long> {
    // Méthode pour récupérer les virements effectués par un expéditeur spécifique
    List<Virement> findByExpediteurId(Integer expediteurId);

    // Méthode pour récupérer les virements reçus par un destinataire spécifique
    List<Virement> findByDestinataireId(Integer destinataireId);
}
