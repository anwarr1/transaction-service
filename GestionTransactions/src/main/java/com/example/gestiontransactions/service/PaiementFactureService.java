package com.example.gestiontransactions.service;

import com.example.gestiontransactions.exception.InsufficientFundsException;
import com.example.gestiontransactions.exception.ResourceNotFoundException;
import com.example.gestiontransactions.model.Compte;
import com.example.gestiontransactions.model.Facture;
import com.example.gestiontransactions.model.PaiementFacture;
import com.example.gestiontransactions.enums.StatutFacture;
import com.example.gestiontransactions.repository.CompteRepository;
import com.example.gestiontransactions.repository.FactureRepository;
import com.example.gestiontransactions.repository.PaiementFactureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaiementFactureService {

    @Autowired
    private PaiementFactureRepository paiementFactureRepository;

    @Autowired
    private CompteRepository compteRepository;

    @Autowired
    private FactureRepository factureRepository;

    public PaiementFacture traiterPaiement(PaiementFacture paiementFacture) {
        // Récupérer le compte associé au paiement
        Compte compte = compteRepository.findById(paiementFacture.getCompte().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Compte non trouvé"));

        // Vérifier si le compte a suffisamment de fonds pour le paiement
        if (compte.getSolde() < paiementFacture.getMontant()) {
            throw new InsufficientFundsException("Solde insuffisant pour effectuer le paiement");
        }

        // Déduire le montant du solde du compte
        compte.setSolde(compte.getSolde() - paiementFacture.getMontant());
        compteRepository.save(compte);

        // Mettre à jour la facture en la marquant comme payée
        Facture facture = factureRepository.findById(paiementFacture.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Facture non trouvée"));
        facture.setStatut(StatutFacture.PAYÉE);
        factureRepository.save(facture);

        // Sauvegarder le paiement de facture
        return paiementFactureRepository.save(paiementFacture);
    }
}
