package com.example.gestiontransactions.service;

import com.example.gestiontransactions.exception.ResourceNotFoundException;
import com.example.gestiontransactions.model.Facture;
import com.example.gestiontransactions.enums.StatutFacture;
import com.example.gestiontransactions.repository.FactureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FactureService {

    @Autowired
    private FactureRepository factureRepository;

    // Créer une nouvelle facture
    public Facture creerFacture(Facture facture) {
        return factureRepository.save(facture); // Sauvegarde la nouvelle facture dans la base de données
    }

    // Récupérer une facture par son ID
    public Facture recupererFacture(Long id) {
        return factureRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Facture non trouvée"));
    }

    // Mettre à jour une facture existante
    public Facture mettreAJourFacture(Long id, Facture factureDetails) {
        Facture facture = factureRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Facture non trouvée"));

        // Mettre à jour les informations de la facture
        facture.setFournisseurId(factureDetails.getFournisseurId());
        facture.setMontant(factureDetails.getMontant());
        facture.setDateLimite(factureDetails.getDateLimite());

        return factureRepository.save(facture); // Sauvegarder la facture mise à jour
    }

    // Supprimer une facture
    public void supprimerFacture(Long id) {
        Facture facture = factureRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Facture non trouvée"));

        factureRepository.delete(facture); // Supprimer la facture de la base de données
    }

    // Marquer une facture comme payée
    public Facture marquerFacturePayee(Long id) {
        Facture facture = factureRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Facture non trouvée"));

        facture.setStatut(StatutFacture.PAYÉE); // Marquer la facture comme payée
        return factureRepository.save(facture); // Sauvegarder la facture mise à jour
    }
}
