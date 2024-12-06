package com.example.gestiontransactions.service;

import com.example.gestiontransactions.exception.ResourceNotFoundException;
import com.example.gestiontransactions.model.Compte;
import com.example.gestiontransactions.repository.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompteService {

    @Autowired
    private CompteRepository compteRepository;

    // Créer un nouveau compte
    public Compte creerCompte(Compte compte) {
        return compteRepository.save(compte); // Sauvegarde le nouveau compte dans la base de données
    }

    // Récupérer un compte par son ID
    public Compte recupererCompte(Long id) {
        return compteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Compte non trouvé"));
    }


    // Mettre à jour un compte existant
    public Compte mettreAJourCompte(Long id, Compte compteDetails) {
        Compte compte = compteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Compte non trouvé"));

        // Mettre à jour les informations du compte
        compte.setSolde(compteDetails.getSolde());
        compte.setDevise(compteDetails.getDevise());
        compte.setIdUser(compteDetails.getIdUser());

        return compteRepository.save(compte); // Sauvegarder le compte mis à jour
    }

    // Supprimer un compte
    public void supprimerCompte(Long id) {
        Compte compte = compteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Compte non trouvé"));

        compteRepository.delete(compte); // Supprimer le compte de la base de données
    }
}
