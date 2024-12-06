package com.example.gestiontransactions.controller;

import com.example.gestiontransactions.model.PaiementEnLigne;
import com.example.gestiontransactions.service.PaiementEnLigneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/paiements-en-ligne")
public class PaiementEnLigneController {

    @Autowired
    private PaiementEnLigneService paiementEnLigneService;

    @PostMapping
    public PaiementEnLigne effectuerPaiement(@RequestBody PaiementEnLigne paiementEnLigne) {
        return paiementEnLigneService.effectuerPaiement(paiementEnLigne);
    }
}
