package com.example.gestiontransactions.controller;

import com.example.gestiontransactions.model.Virement;
import com.example.gestiontransactions.service.VirementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/virements")
public class VirementController {

    @Autowired
    private VirementService virementService;

    @PostMapping
    public Virement effectuerVirement(@RequestBody Virement virement) {
        return virementService.effectuerVirement(virement);
    }
}
