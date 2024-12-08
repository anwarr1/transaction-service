package com.example.gestiontransactions.integration.controller;

import com.example.gestiontransactions.controller.PaiementReccurentController;
import com.example.gestiontransactions.enums.FrequencePaiement;
import com.example.gestiontransactions.model.PaiementReccurent;
import com.example.gestiontransactions.service.PaiementReccurentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.Mockito.when;

import java.util.Date;

public class PaiementReccurentControllerIT {

    private MockMvc mockMvc;

    @Mock
    private PaiementReccurentService paiementReccurentService;

    @InjectMocks
    private PaiementReccurentController paiementReccurentController;

    private PaiementReccurent paiementReccurent;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(paiementReccurentController).build();

        paiementReccurent = new PaiementReccurent();
        paiementReccurent.setId(1L);
        paiementReccurent.setFrequence(FrequencePaiement.MENSUEL);
        paiementReccurent.setProchainePaiementDate(new Date());
        paiementReccurent.setCompte(null);
    }

    @Test
    public void testPlanifierPaiement() throws Exception {
        when(paiementReccurentService.planifierPaiement(org.mockito.ArgumentMatchers.any()))
                .thenReturn(paiementReccurent);

        mockMvc.perform(post("/api/paiements-recurrents")
                        .contentType("application/json")
                        .content("{\"frequence\":\"MENSUEL\",\"prochainePaiementDate\":\"2024-12-31\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.frequence").value("MENSUEL"));
    }

    @Test
    public void testAnnulerPaiement() throws Exception {
        mockMvc.perform(put("/api/paiements-recurrents/1/annuler"))
                .andExpect(status().isOk());
    }
}
