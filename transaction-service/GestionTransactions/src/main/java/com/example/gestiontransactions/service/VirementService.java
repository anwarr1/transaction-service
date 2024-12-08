package com.example.gestiontransactions.service;

import com.example.gestiontransactions.exception.ResourceNotFoundException;
import com.example.gestiontransactions.model.Compte;
import com.example.gestiontransactions.model.Virement;
import com.example.gestiontransactions.repository.CompteRepository;
import com.example.gestiontransactions.repository.VirementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VirementService {

    @Autowired
    private VirementRepository virementRepository;

    @Autowired
    private CompteRepository compteRepository;

    public Virement effectuerVirement(Virement virement) {
        // Logique métier pour effectuer le virement entre l'expéditeur et le destinataire
        Compte expediteur = compteRepository.findById(virement.getExpediteur().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Compte expéditeur non trouvé"));
        Compte destinataire = compteRepository.findById(virement.getDestinataire().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Compte destinataire non trouvé"));

        // Effectuer le virement
        expediteur.setSolde(expediteur.getSolde() - virement.getMontant());
        destinataire.setSolde(destinataire.getSolde() + virement.getMontant());

        compteRepository.save(expediteur);
        compteRepository.save(destinataire);

        return virementRepository.save(virement);
    }
}
