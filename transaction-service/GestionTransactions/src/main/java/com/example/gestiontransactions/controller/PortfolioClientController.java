package com.example.gestiontransactions.controller;

import com.example.gestiontransactions.dto.Portefeuille;
import com.example.gestiontransactions.external.ExternalPortfolioService;
import com.example.gestiontransactions.service.PortfolioClientService;
import com.example.gestiontransactions.service.VirementService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/portfolio")
public class PortfolioClientController {

    private final ExternalPortfolioService externalPortfolioService;
    private final VirementService virementService;

    public PortfolioClientController(ExternalPortfolioService externalPortfolioService, VirementService virementService) {
        this.externalPortfolioService = externalPortfolioService;
        this.virementService = virementService;
    }

    @GetMapping("/test")
    public String testRemoteCall() {
        return externalPortfolioService.test();
    }


}
