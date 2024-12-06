package com.example.gestiontransactions.controller;

import com.example.gestiontransactions.model.PaiementFacture;
import com.example.gestiontransactions.service.PaiementFactureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/paiements")
public class PaiementFactureController {

    @Autowired
    private PaiementFactureService paiementFactureService;

    @PostMapping
    public PaiementFacture traiterPaiement(@RequestBody PaiementFacture paiementFacture) {
        return paiementFactureService.traiterPaiement(paiementFacture);
    }
}
