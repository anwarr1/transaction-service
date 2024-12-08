package com.example.gestiontransactions.service;

import com.example.gestiontransactions.exception.InsufficientFundsException;
import com.example.gestiontransactions.exception.ResourceNotFoundException;
import com.example.gestiontransactions.model.Compte;
import com.example.gestiontransactions.model.PaiementEnLigne;
import com.example.gestiontransactions.repository.CompteRepository;
import com.example.gestiontransactions.repository.PaiementEnLigneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaiementEnLigneService {

    @Autowired
    private PaiementEnLigneRepository paiementEnLigneRepository;

    @Autowired
    private CompteRepository compteRepository;

    public PaiementEnLigne effectuerPaiement(PaiementEnLigne paiementEnLigne) {
        // Récupérer le compte associé au paiement
        Compte compte = compteRepository.findById(paiementEnLigne.getCompte().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Compte non trouvé"));

        // Vérifier si le compte a suffisamment de fonds pour le paiement
        if (compte.getSolde() < paiementEnLigne.getMontant()) {
            throw new InsufficientFundsException("Solde insuffisant pour effectuer le paiement");
        }

        // Déduire le montant du solde du compte
        compte.setSolde(compte.getSolde() - paiementEnLigne.getMontant());
        compteRepository.save(compte);

        // Sauvegarder le paiement en ligne
        return paiementEnLigneRepository.save(paiementEnLigne);
    }
}
