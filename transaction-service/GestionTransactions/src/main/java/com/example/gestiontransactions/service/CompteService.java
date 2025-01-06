package com.example.gestiontransactions.service;

import com.example.gestiontransactions.exception.InsufficientFundsException;
import com.example.gestiontransactions.exception.ResourceNotFoundException;
import com.example.gestiontransactions.external.ExternalPortfolioService;
import com.example.gestiontransactions.model.Compte;
import com.example.gestiontransactions.repository.CompteRepository;
import com.example.gestiontransactions.request.CompteRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

@Service
public class CompteService {

    @Autowired
    private CompteRepository compteRepository;
    @Autowired
    private ExternalPortfolioService externalPortfolioService;

    // Créer un nouveau compte
    public Compte creerCompte(Compte compte) {
        CompteRequest compteRequest = new CompteRequest();
        compteRequest.setSomme(compte.getSolde());
        compteRequest.setUtilisateurId(Long.valueOf(compte.getIdUser()));
        externalPortfolioService.sendCompte(compteRequest);

        return compteRepository.save(compte); // Sauvegarde le nouveau compte dans la base de données
    }

    public void retirerDuCompte(Long compteId, double montant) {
        // Récupérer le compte correspondant
        Compte compte = compteRepository.findById(compteId)
                .orElseThrow(() -> new ResourceNotFoundException("Compte non trouvé"));

        // Vérifier si le solde est suffisant
        if (compte.getSolde() < montant) {
            throw new InsufficientFundsException("Solde insuffisant pour créer une carte virtuelle");
        }

        compte.setSolde(compte.getSolde() - montant);

        // Sauvegarder le compte mis à jour
        compteRepository.save(compte);

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
    public static String generateRIB(Long id, String bank) throws IllegalArgumentException {
        // Dictionnaire des codes banques et guichets
        Map<String, String> bankCodes = new HashMap<>();
        bankCodes.put("CIH", "19");  // Code banque CIH
        bankCodes.put("BMCE", "01"); // Code banque BMCE
        bankCodes.put("CDM", "02");  // Code banque CDM
        bankCodes.put("WAFABANK", "08"); // Code banque WAFABANK

        Map<String, String> guichetCodes = new HashMap<>();
        guichetCodes.put("CIH", "019");  // Guichet principal CIH
        guichetCodes.put("BMCE", "111"); // Guichet principal BMCE
        guichetCodes.put("CDM", "017");  // Guichet principal CDM
        guichetCodes.put("WAFABANK", "023"); // Guichet principal WAFABANK

        // Récupérer le code banque pour la banque spécifiée
        String codeBanque = bankCodes.get(bank.toUpperCase());
        String codeGuichet = guichetCodes.get(bank.toUpperCase());

        if (codeBanque == null || codeGuichet == null) {
            throw new IllegalArgumentException("La banque spécifiée est invalide.");
        }

        // Ajout des zéros pour s'assurer que l'ID est sur 10 chiffres
        String accountNumber = String.format("%010d", id);

        // Concaténer pour former la base du RIB
        String ribBase = codeBanque + codeGuichet + accountNumber;

        // Calcul de la clé RIB (modulo 97)
        int ribKey = 97 - (int) (Long.parseLong(ribBase) % 97);

        // Formatage de la clé sur 2 chiffres
        DecimalFormat df = new DecimalFormat("00");
        String formattedRibKey = df.format(ribKey);

        // Construire le RIB final
        return codeBanque + codeGuichet + accountNumber + formattedRibKey;
    }

}
