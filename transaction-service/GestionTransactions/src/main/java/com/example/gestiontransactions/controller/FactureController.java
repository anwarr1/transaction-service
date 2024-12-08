package com.example.gestiontransactions.controller;

import com.example.gestiontransactions.model.Facture;
import com.example.gestiontransactions.service.FactureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/factures")
public class FactureController {

    @Autowired
    private FactureService factureService;

    // Créer une nouvelle facture
    @PostMapping
    public Facture creerFacture(@RequestBody Facture facture) {
        return factureService.creerFacture(facture);
    }

    // Récupérer une facture par son ID
    @GetMapping("/{id}")
    public Facture recupererFacture(@PathVariable Long id) {
        return factureService.recupererFacture(id);
    }

    // Mettre à jour une facture existante
    @PutMapping("/{id}")
    public Facture mettreAJourFacture(@PathVariable Long id, @RequestBody Facture factureDetails) {
        return factureService.mettreAJourFacture(id, factureDetails);
    }

    // Supprimer une facture
    @DeleteMapping("/{id}")
    public void supprimerFacture(@PathVariable Long id) {
        factureService.supprimerFacture(id);
    }

    // Marquer une facture comme payée
    @PutMapping("/{id}/payer")
    public Facture marquerFacturePayee(@PathVariable Long id) {
        return factureService.marquerFacturePayee(id);
    }
}
