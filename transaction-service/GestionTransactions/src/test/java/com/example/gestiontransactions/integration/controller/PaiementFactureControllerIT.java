package com.example.gestiontransactions.integration.controller;

import com.example.gestiontransactions.controller.PaiementFactureController;
import com.example.gestiontransactions.model.Fournisseur;
import com.example.gestiontransactions.model.PaiementFacture;
import com.example.gestiontransactions.service.PaiementFactureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class PaiementFactureControllerIT {

    @Mock
    private PaiementFactureService paiementFactureService;

    @InjectMocks
    private PaiementFactureController paiementFactureController;

    private MockMvc mockMvc;

    private PaiementFacture paiementFacture;
    private Fournisseur fournisseur;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
        mockMvc = MockMvcBuilders.standaloneSetup(paiementFactureController).build(); // Configure MockMvc with the controller

        // Create mock Fournisseur
        fournisseur = new Fournisseur();
        fournisseur.setId(1L);
        fournisseur.setNom("Test Fournisseur");

        // Initialize PaiementFacture with a Fournisseur object
        paiementFacture = new PaiementFacture();
        paiementFacture.setFournisseur(fournisseur);
        paiementFacture.setMontant(100.0);

        // Simulate the expected behavior of the service
        when(paiementFactureService.traiterPaiement(any())).thenReturn(paiementFacture);
    }

    @Test
    public void testTraiterPaiement() throws Exception {
        // JSON payload with supplier ID
        String requestBody = """
                {
                    "fournisseur": { "id": 1, "nom": "Test Fournisseur" },
                    "montant": 100.0
                }
                """;

        mockMvc.perform(post("/api/paiements")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fournisseur.nom").value("Test Fournisseur"))
                .andExpect(jsonPath("$.montant").value(100.0));
    }
}
