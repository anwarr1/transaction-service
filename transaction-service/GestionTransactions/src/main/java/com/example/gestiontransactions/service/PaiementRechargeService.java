//package com.example.gestiontransactions.service;
//
//import com.example.gestiontransactions.enums.StatutFacture;
//import com.example.gestiontransactions.enums.TypeService;
//import com.example.gestiontransactions.exception.InsufficientFundsException;
//import com.example.gestiontransactions.exception.ResourceNotFoundException;
//import com.example.gestiontransactions.external.ExternalPortfolioService;
//import com.example.gestiontransactions.model.Compte;
//import com.example.gestiontransactions.model.Facture;
//import com.example.gestiontransactions.model.PaiementRecharge;
//import com.example.gestiontransactions.repository.CompteRepository;
//import com.example.gestiontransactions.repository.FactureRepository;
//import com.example.gestiontransactions.repository.PaiementFactureRepository;
//import com.example.gestiontransactions.repository.PaiementRechargeRepository;
//import com.example.gestiontransactions.request.FactureRequest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//
//@Service
//public class PaiementRechargeService {
//
//    @Autowired
//    private PaiementRechargeRepository paiementRechargeRepository;
//    @Autowired
//    private CompteRepository compteRepository;
//    @Autowired
//    private FactureRepository factureRepository;
//
//
//
//    public void Recharge(PaiementRecharge paiementRecharge) {
//        int montant = 20;
//        Facture facture = new Facture();
//        facture.setType_facture(TypeService.RECHARGE);
//        facture.setMontant(50.0);
//
//        facture.setStatut(StatutFacture.PAYÉE);
//
//        paiementRecharge.setDate(new Date()); // Utilise la date et l'heure actuelles
//
//        Compte compte = compteRepository.findById(paiementRecharge.getCompte().getId())
//                .orElseThrow(() -> new ResourceNotFoundException("Compte non trouvé"));
//
//        if (compte.getSolde() < montant) {
//            throw new InsufficientFundsException("Solde insuffisant pour effectuer le paiement");
//        }
//
//        compte.setSolde(compte.getSolde() - montant);
//        compteRepository.save(compte);
//
//        facture = factureRepository.save(facture);
//        FactureRequest factureRequest = new FactureRequest();
//        factureRequest.setSomme(50.0);
//        factureRequest.setUtilisateurId(Long.valueOf(compte.getIdUser()));
////        externalPortfolioService.updateFacture(factureRequest);
//        // Sauvegarder le paiement de facture
//        System.out.println("Recharge recharge: " + facture.getType_facture());
//
//
//
//        Facture facture1 = factureRepository.findById(facture.getId()).get();
//
//        paiementRecharge.setFacture(facture1);
//        paiementRecharge.setCompte(compte);
//        paiementFactureRepository.save(paiementRecharge);
//
//    }
//
//}
