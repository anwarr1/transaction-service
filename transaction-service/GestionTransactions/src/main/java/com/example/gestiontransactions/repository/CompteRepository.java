package com.example.gestiontransactions.repository;

import com.example.gestiontransactions.dto.CompteDTO;
import com.example.gestiontransactions.model.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompteRepository extends JpaRepository<Compte, Long> {
    // Custom query methods can be added here if needed
    // Custom query methods can be added here if needed
    @Query("SELECT COUNT(*) FROM Compte c WHERE c.idUser = :userId")
    int NbrCompte(Integer userId);
    @Query("SELECT new com.example.gestiontransactions.dto.CompteDTO(c.id, c.solde, c.idUser,c.Bank,c.rib,c.devise) " +
            "FROM Compte c WHERE c.idUser = :userId")
    List<CompteDTO> findByIdUser(Integer userId);
    @Query("SELECT c FROM Compte c WHERE c.rib = :rib")
    Compte findByRib(String rib);
}