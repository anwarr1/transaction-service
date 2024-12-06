package com.example.gestiontransactions.controller;

import com.example.gestiontransactions.model.Compte;
import com.example.gestiontransactions.service.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comptes")
public class CompteController {

    @Autowired
    private CompteService compteService;

    // Créer un nouveau compte
    @PostMapping
    public Compte creerCompte(@RequestBody Compte compte) {
        return compteService.creerCompte(compte);
    }

    // Récupérer un compte par son ID
    @GetMapping("/{id}")
    public Compte recupererCompte(@PathVariable Long id) {
        return compteService.recupererCompte(id);
    }

    // Mettre à jour un compte existant
    @PutMapping("/{id}")
    public Compte mettreAJourCompte(@PathVariable Long id, @RequestBody Compte compteDetails) {
        return compteService.mettreAJourCompte(id, compteDetails);
    }

    // Supprimer un compte
    @DeleteMapping("/{id}")
    public void supprimerCompte(@PathVariable Long id) {
        compteService.supprimerCompte(id);
    }
}
