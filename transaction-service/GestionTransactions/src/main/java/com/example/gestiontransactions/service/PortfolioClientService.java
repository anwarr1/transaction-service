package com.example.gestiontransactions.service;

import com.example.gestiontransactions.dto.Portefeuille;
import com.example.gestiontransactions.external.ExternalPortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PortfolioClientService {


    private final ExternalPortfolioService externalPortfolioService;

    public PortfolioClientService(ExternalPortfolioService externalPortfolioService) {
        this.externalPortfolioService = externalPortfolioService;
    }

    public String callRemoteTest() {
        return externalPortfolioService.test();
    }

}
