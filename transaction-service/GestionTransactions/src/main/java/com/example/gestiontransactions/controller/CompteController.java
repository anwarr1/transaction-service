package com.example.gestiontransactions.controller;

import com.example.gestiontransactions.dto.CompteDTO;
import com.example.gestiontransactions.dto.RetirerDuCompte;
import com.example.gestiontransactions.model.Compte;
import com.example.gestiontransactions.repository.CompteRepository;
import com.example.gestiontransactions.service.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comptes")
public class CompteController {

    @Autowired
    private CompteService compteService;
    @Autowired
    private CompteRepository compteRepository;

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

    // Mettre à jour un compte existant
    @PostMapping("/retirer")
    void retirerCompte(@RequestBody RetirerDuCompte retirerDuCompte) {
        compteService.retirerDuCompte(retirerDuCompte.getCompteId(), retirerDuCompte.getMontant());
    }

    // Supprimer un compte
    @DeleteMapping("/{id}")
    public void supprimerCompte(@PathVariable Long id) {
        compteService.supprimerCompte(id);
    }
    @GetMapping("/nbrCompte/{id}")
    public int nbrCompte(@PathVariable Integer id) {
        return compteRepository.NbrCompte(id);
    }
    @GetMapping("/user/{id}")
    public List<CompteDTO> getCompteByUser(@PathVariable Integer id) {
        return compteRepository.findByIdUser(id);
    }
    @PostMapping("/retirer/{id}")
    public void retirer(@PathVariable Long id, @RequestParam Double montant) {
        compteService.retirerDuCompte(id,montant);
    }
}
