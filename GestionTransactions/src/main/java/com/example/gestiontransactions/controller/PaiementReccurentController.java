package com.example.gestiontransactions.controller;

import com.example.gestiontransactions.model.PaiementReccurent;
import com.example.gestiontransactions.service.PaiementReccurentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/paiements-recurrents")
public class PaiementReccurentController {

    @Autowired
    private PaiementReccurentService paiementReccurentService;

    @PostMapping
    public PaiementReccurent planifierPaiement(@RequestBody PaiementReccurent paiementReccurent) {
        return paiementReccurentService.planifierPaiement(paiementReccurent);
    }

    @PutMapping("/{id}/annuler")
    public void annulerPaiement(@PathVariable Long id) {
        paiementReccurentService.annulerPaiement(id);
    }
}
