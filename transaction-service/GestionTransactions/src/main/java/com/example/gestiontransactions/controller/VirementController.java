package com.example.gestiontransactions.controller;

import com.example.gestiontransactions.model.Virement;
import com.example.gestiontransactions.request.VirementRequest;
import com.example.gestiontransactions.service.VirementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/virements")
public class VirementController {

    @Autowired
    private VirementService virementService;

    @PostMapping
    public Virement effectuerVirement(@RequestBody VirementRequest virementRequest) {
        return virementService.effectuerVirement(virementRequest.getVirement(), virementRequest.getSms());
    }
}
