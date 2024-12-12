package com.example.gestiontransactions.external;

import com.example.gestiontransactions.dto.UpdatePortefeuille;
import com.example.gestiontransactions.model.Compte;
import com.example.gestiontransactions.model.Virement;
import com.example.gestiontransactions.request.CompteRequest;
import com.example.gestiontransactions.request.FactureRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "gestion-portefeuilles", url = "http://192.168.91.211:8043/gestion_portefeuille/portefeuille")
public interface ExternalPortfolioService {

    @GetMapping("/test")
    String test();

    @PostMapping("/update")
    Virement sendVirement(
            @RequestBody UpdatePortefeuille portefeuilleUpdateRequest

    );

    @PostMapping("/updateFacture")
    Virement updateFacture(
            @RequestBody FactureRequest factureRequest

    );

    @PostMapping("/ajoutCompte")
    Compte sendCompte(
            @RequestBody CompteRequest compteRequest

    );
}


