package com.example.gestiontransactions.integration.controller;

import com.example.gestiontransactions.controller.PaiementEnLigneController;
import com.example.gestiontransactions.model.PaiementEnLigne;
import com.example.gestiontransactions.service.PaiementEnLigneService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class PaiementEnLigneControllerIT {

    @Mock
    private PaiementEnLigneService paiementEnLigneService;

    @InjectMocks
    private PaiementEnLigneController paiementEnLigneController;

    private MockMvc mockMvc;

    private PaiementEnLigne paiementEnLigne;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialise les mocks
        mockMvc = MockMvcBuilders.standaloneSetup(paiementEnLigneController).build(); // Configure MockMvc avec le contrôleur

        paiementEnLigne = new PaiementEnLigne();
        paiementEnLigne.setFournisseur("Test Fournisseur");
        paiementEnLigne.setReferenceTransaction("TX123");

        // Simule le comportement attendu du service
        when(paiementEnLigneService.effectuerPaiement(any())).thenReturn(paiementEnLigne);
    }

    @Test
    public void testEffectuerPaiement() throws Exception {
        mockMvc.perform(post("/api/paiements-en-ligne")
                        .contentType("application/json")
                        .content("{\"fournisseur\":\"Test Fournisseur\",\"referenceTransaction\":\"TX123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fournisseur").value("Test Fournisseur"))
                .andExpect(jsonPath("$.referenceTransaction").value("TX123"));
    }
}
