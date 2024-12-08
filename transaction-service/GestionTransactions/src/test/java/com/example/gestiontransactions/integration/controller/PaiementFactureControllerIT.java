package com.example.gestiontransactions.integration.controller;

import com.example.gestiontransactions.controller.PaiementFactureController;
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

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialise les mocks
        mockMvc = MockMvcBuilders.standaloneSetup(paiementFactureController).build(); // Configure MockMvc avec le contrôleur

        paiementFacture = new PaiementFacture();
        paiementFacture.setFournisseur("Test Fournisseur");
        paiementFacture.setMontant(100.0);

        // Simule le comportement attendu du service
        when(paiementFactureService.traiterPaiement(any())).thenReturn(paiementFacture);
    }

    @Test
    public void testTraiterPaiement() throws Exception {
        mockMvc.perform(post("/api/paiements")
                        .contentType("application/json")
                        .content("{\"fournisseur\":\"Test Fournisseur\",\"montant\":100.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fournisseur").value("Test Fournisseur"))
                .andExpect(jsonPath("$.montant").value(100.0));
    }
}
