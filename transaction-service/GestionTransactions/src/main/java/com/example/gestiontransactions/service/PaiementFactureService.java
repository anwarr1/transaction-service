package com.example.gestiontransactions.service;

import com.example.gestiontransactions.enums.StatutFacture;
import com.example.gestiontransactions.exception.InsufficientFundsException;
import com.example.gestiontransactions.exception.ResourceNotFoundException;
import com.example.gestiontransactions.external.ExternalPortfolioService;
import com.example.gestiontransactions.model.Compte;
import com.example.gestiontransactions.model.Facture;
import com.example.gestiontransactions.model.PaiementFacture;
import com.example.gestiontransactions.repository.CompteRepository;
import com.example.gestiontransactions.repository.FactureRepository;
import com.example.gestiontransactions.repository.PaiementFactureRepository;
import com.example.gestiontransactions.request.FactureRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaiementFactureService {

    private final ExternalPortfolioService externalPortfolioService;
    @Autowired
    private PaiementFactureRepository paiementFactureRepository;
    @Autowired
    private CompteRepository compteRepository;
    @Autowired
    private FactureRepository factureRepository;

    public PaiementFactureService(ExternalPortfolioService externalPortfolioService) {
        this.externalPortfolioService = externalPortfolioService;
    }

    public PaiementFacture traiterPaiement(PaiementFacture paiementFacture) {
        paiementFacture.setMontant(factureRepository.findById(paiementFacture.getFacture().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Facture non trouvée")).getMontant());
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
        Facture facture = factureRepository.findById(paiementFacture.getFacture().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Facture non trouvée"));
        facture.setStatut(StatutFacture.PAYÉE);

        factureRepository.save(facture);
        FactureRequest factureRequest = new FactureRequest();
        factureRequest.setSomme(paiementFacture.getMontant());
        factureRequest.setUtilisateurId(Long.valueOf(compte.getIdUser()));
        externalPortfolioService.updateFacture(factureRequest);
        // Sauvegarder le paiement de facture
        return paiementFactureRepository.save(paiementFacture);
    }
}
