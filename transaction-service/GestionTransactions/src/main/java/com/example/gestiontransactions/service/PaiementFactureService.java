package com.example.gestiontransactions.service;

import com.example.gestiontransactions.enums.StatutFacture;
import com.example.gestiontransactions.enums.TypeService;
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
        System.out.println("Paiement facture solde: " + paiementFacture.getCompte().getSolde());
        System.out.println("Paiement facture solde: " + paiementFacture.getFacture().getMontant());
        double montant = paiementFacture.getFacture().getMontant();
        paiementFacture.setMontant(montant);
        // Récupérer le compte associé au paiement
        Compte compte = compteRepository.findById(paiementFacture.getCompte().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Compte non trouvé"));

        // Vérifier si le compte a suffisamment de fonds pour le paiement
        if (compte.getSolde() < montant) {
            throw new InsufficientFundsException("Solde insuffisant pour effectuer le paiement");
        }

        // Déduire le montant du solde du compte
        compte.setSolde(compte.getSolde() - montant);
        compteRepository.save(compte);

        // Mettre à jour la facture en la marquant comme payée
        Facture facture = factureRepository.findById(paiementFacture.getFacture().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Facture non trouvée"));
        facture.setStatut(StatutFacture.PAYÉE);

        factureRepository.save(facture);
        FactureRequest factureRequest = new FactureRequest();
        factureRequest.setSomme(montant);
        factureRequest.setUtilisateurId(Long.valueOf(compte.getIdUser()));
        externalPortfolioService.updateFacture(factureRequest);
        // Sauvegarder le paiement de facture
        Facture facture1 = factureRepository.findById(paiementFacture.getFacture().getId()).get();
        paiementFacture.setFacture(facture1);
        return paiementFactureRepository.save(paiementFacture);

    }

    public void Recharge(PaiementFacture paiementFacture) {

        double montant = 20;
        Facture facture = new Facture();
        facture.setType_facture(TypeService.RECHARGE);
        facture.setMontant(montant);
        facture.setStatut(StatutFacture.PAYÉE);

        paiementFacture.setMontant(montant);

        Compte compte = compteRepository.findById(paiementFacture.getCompte().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Compte non trouvé"));

        if (compte.getSolde() < montant) {
            throw new InsufficientFundsException("Solde insuffisant pour effectuer le paiement");
        }

        compte.setSolde(compte.getSolde() - montant);
        compteRepository.save(compte);

        factureRepository.save(facture);
        FactureRequest factureRequest = new FactureRequest();
        factureRequest.setSomme(montant);
        factureRequest.setUtilisateurId(Long.valueOf(compte.getIdUser()));
        externalPortfolioService.updateFacture(factureRequest);
        // Sauvegarder le paiement de facture
        paiementFacture.setFacture(facture);
        paiementFactureRepository.save(paiementFacture);

    }

    public void Don(PaiementFacture paiementFacture) {

        double montant = 20;
        Facture facture = new Facture();
        facture.setType_facture(TypeService.DON);
        facture.setMontant(montant);
        facture.setStatut(StatutFacture.PAYÉE);

        paiementFacture.setMontant(montant);

        Compte compte = compteRepository.findById(paiementFacture.getCompte().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Compte non trouvé"));

        if (compte.getSolde() < montant) {
            throw new InsufficientFundsException("Solde insuffisant pour effectuer le paiement");
        }

        compte.setSolde(compte.getSolde() - montant);
        compteRepository.save(compte);

        factureRepository.save(facture);
        FactureRequest factureRequest = new FactureRequest();
        factureRequest.setSomme(montant);
        factureRequest.setUtilisateurId(Long.valueOf(compte.getIdUser()));
        externalPortfolioService.updateFacture(factureRequest);
        // Sauvegarder le paiement de facture
        paiementFacture.setFacture(facture);
        paiementFactureRepository.save(paiementFacture);

    }
}
