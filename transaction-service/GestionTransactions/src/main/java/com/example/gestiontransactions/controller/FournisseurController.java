package com.example.gestiontransactions.controller;

import com.example.gestiontransactions.model.Fournisseur;
import com.example.gestiontransactions.repository.FournisseurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/fournisseurs")
@CrossOrigin(origins = "http://localhost:4200")
public class FournisseurController {
    @Autowired
    private FournisseurRepository fournisseurRepository;

    // Créer une nouvelle facture
    @GetMapping
    public List<Fournisseur> findFournisseurs() {
        return fournisseurRepository.findAll();
    }

}
