package com.example.gestiontransactions.service;

import com.example.gestiontransactions.exception.ResourceNotFoundException;
import com.example.gestiontransactions.model.Compte;
import com.example.gestiontransactions.model.PaiementReccurent;
import com.example.gestiontransactions.repository.CompteRepository;
import com.example.gestiontransactions.repository.PaiementReccurentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PaiementReccurentService {

    @Autowired
    private PaiementReccurentRepository paiementReccurentRepository;

    @Autowired
    private CompteRepository compteRepository;

    public PaiementReccurent planifierPaiement(PaiementReccurent paiementReccurent) {
        // Logique pour planifier un paiement récurrent
        Compte compte = compteRepository.findById(paiementReccurent.getCompte().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Compte non trouvé"));

        // Logique de planification (par exemple, définir la prochaine date de paiement)
        paiementReccurent.setProchainePaiementDate(new Date()); // Remplacer par une logique réelle
        return paiementReccurentRepository.save(paiementReccurent);
    }

    public void annulerPaiement(Long paiementId) {
        PaiementReccurent paiementReccurent = paiementReccurentRepository.findById(paiementId)
                .orElseThrow(() -> new ResourceNotFoundException("Paiement récurrent non trouvé"));

        paiementReccurentRepository.delete(paiementReccurent); // Annuler le paiement récurrent
    }
}
