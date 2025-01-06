package com.example.gestiontransactions.controller;

import com.example.gestiontransactions.model.Compte;
import com.example.gestiontransactions.model.Virement;
import com.example.gestiontransactions.repository.CompteRepository;
import com.example.gestiontransactions.request.VirementRequest;
import com.example.gestiontransactions.service.VirementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/virements")
@CrossOrigin(origins = "http://localhost:4200")
public class VirementController {

    @Autowired
    private VirementService virementService;
    @Autowired

    private CompteRepository compteRepository;

    @PostMapping
    public Virement effectuerVirement(@RequestBody VirementRequest virementRequest) {
        return virementService.effectuerVirement(virementRequest.getVirement(), virementRequest.getSms());
    }

    @GetMapping("/{rib}")
    public Compte getCompteByRib(@PathVariable String rib) {
        return compteRepository.findByRib(rib);
    }
}
