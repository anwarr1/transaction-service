package com.example.gestiontransactions.service;

import com.example.gestiontransactions.enums.StatutFacture;
import com.example.gestiontransactions.enums.TypeService;
import com.example.gestiontransactions.enums.TypeTransaction;
import com.example.gestiontransactions.exception.InsufficientFundsException;
import com.example.gestiontransactions.exception.ResourceNotFoundException;
import com.example.gestiontransactions.external.ExternalPortfolioService;
import com.example.gestiontransactions.model.Compte;
import com.example.gestiontransactions.model.Facture;
import com.example.gestiontransactions.model.Fournisseur;
import com.example.gestiontransactions.model.PaiementFacture;
import com.example.gestiontransactions.repository.CompteRepository;
import com.example.gestiontransactions.repository.FactureRepository;
import com.example.gestiontransactions.repository.FournisseurRepository;
import com.example.gestiontransactions.repository.PaiementFactureRepository;
import com.example.gestiontransactions.request.FactureRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PaiementFactureService {

    private final ExternalPortfolioService externalPortfolioService;
    @Autowired
    private PaiementFactureRepository paiementFactureRepository;
    @Autowired
    private CompteRepository compteRepository;
    @Autowired
    private FactureRepository factureRepository;
    @Autowired
    private FournisseurRepository fournisseurRepository;

    public PaiementFactureService(ExternalPortfolioService externalPortfolioService,
                                  FournisseurRepository fournisseurRepository, FactureRepository factureRepository, PaiementFactureRepository paiementFactureRepository, CompteRepository compteRepository) {
        this.compteRepository = compteRepository;
        this.externalPortfolioService = externalPortfolioService;
        this.factureRepository = factureRepository;
        this.fournisseurRepository = fournisseurRepository;
        this.paiementFactureRepository = paiementFactureRepository;
    }

    public PaiementFacture traiterPaiement(PaiementFacture paiementFacture) {

        Facture facture = factureRepository.findById(paiementFacture.getFacture().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Facture non trouvée"));
        double montant = facture.getMontant();
        paiementFacture.setMontant(montant);
        // Récupérer le compte associé au paiement
        Compte compte = compteRepository.findById(paiementFacture.getCompte().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Compte non trouvé"));
        paiementFacture.setDate(new Date()); // Utilise la date et l'heure actuelles

        // Vérifier si le compte a suffisamment de fonds pour le paiement
        if (compte.getSolde() < montant) {
            throw new InsufficientFundsException("Solde insuffisant pour effectuer le paiement");
        }

        // Déduire le montant du solde du compte
        compte.setSolde(compte.getSolde() - montant);
        compteRepository.save(compte);

        // Mettre à jour la facture en la marquant comme payée
        ;
        facture.setStatut(StatutFacture.PAYÉE);

        factureRepository.save(facture);
        FactureRequest factureRequest = new FactureRequest();
        factureRequest.setSomme(montant);
        factureRequest.setUtilisateurId(Long.valueOf(compte.getIdUser()));
//        externalPortfolioService.updateFacture(factureRequest);
        // Sauvegarder le paiement de facture
        Facture facture1 = factureRepository.findById(paiementFacture.getFacture().getId()).get();
        paiementFacture.setFacture(facture1);
        return paiementFactureRepository.save(paiementFacture);

    }

    public void Recharge(PaiementFacture paiementFacture) {
        Double montant = paiementFacture.getFacture().getMontant();

        Compte compte = compteRepository.findById(paiementFacture.getCompte().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Compte non trouvé"));

        if (paiementFacture.getFacture().getFournisseur().getId() == 0)
            throw new InsufficientFundsException("aucun fournisseur ");
        if (montant == null || montant == 0)
            throw new InsufficientFundsException("Montant null");
        if (compte.getSolde() < montant) {
            throw new InsufficientFundsException("Solde insuffisant pour effectuer le paiement");
        }
        compte.setSolde(compte.getSolde() - montant);
        compteRepository.save(compte);

        Facture facture = new Facture();
        facture.setType_facture(TypeService.RECHARGE);
        facture.setMontant(montant);

        facture.setStatut(StatutFacture.PAYÉE);

        paiementFacture.setDate(new Date()); // Utilise la date et l'heure actuelles
        Fournisseur f = fournisseurRepository.findById(paiementFacture.getFacture().getFournisseur().getId()).get();
        facture.setFournisseur(f);
        facture = factureRepository.save(facture);
        System.out.println("paiementFacture id " + facture.getId());

        FactureRequest factureRequest = new FactureRequest();
        factureRequest.setSomme(montant);
        factureRequest.setUtilisateurId(Long.valueOf(compte.getIdUser()));
//        externalPortfolioService.updateFacture(factureRequest);
        // Sauvegarder le paiement de facture
        System.out.println("Recharge recharge: " + facture.getType_facture());

        Facture facture1 = factureRepository.findById(facture.getId()).get();

        paiementFacture.setFacture(facture1);
        paiementFacture.setCompte(compte);
        paiementFacture.setTypeTransaction(TypeTransaction.PAIEMENT_FACTURE);
        paiementFacture.setMontant(montant);
        paiementFacture.setIdUser(1);

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
