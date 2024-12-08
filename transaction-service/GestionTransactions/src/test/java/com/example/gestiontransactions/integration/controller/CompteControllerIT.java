package com.example.gestiontransactions.integration.controller;

import com.example.gestiontransactions.controller.CompteController;
import com.example.gestiontransactions.model.Compte;
import com.example.gestiontransactions.service.CompteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

public class CompteControllerIT {

    private MockMvc mockMvc;

    @Mock
    private CompteService compteService;

    @InjectMocks
    private CompteController compteController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialise les mocks
        mockMvc = MockMvcBuilders.standaloneSetup(compteController).build(); // Configure MockMvc avec le contrôleur
    }

    @Test
    public void testCreerCompte() throws Exception {
        Compte compte = new Compte();
        compte.setId(1L);
        compte.setSolde(1000.0);
        compte.setDevise("USD");

        when(compteService.creerCompte(any(Compte.class))).thenReturn(compte);

        mockMvc.perform(post("/api/comptes")
                        .contentType("application/json")
                        .content("{\"solde\":1000.0,\"devise\":\"USD\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.solde").value(1000.0))
                .andExpect(jsonPath("$.devise").value("USD"));

        verify(compteService, times(1)).creerCompte(any());
    }

    @Test
    public void testRecupererCompte() throws Exception {
        Compte compte = new Compte();
        compte.setId(1L);
        compte.setSolde(1000.0);
        compte.setDevise("EUR");

        when(compteService.recupererCompte(1L)).thenReturn(compte);

        mockMvc.perform(get("/api/comptes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.solde").value(1000.0))
                .andExpect(jsonPath("$.devise").value("EUR"));

        verify(compteService, times(1)).recupererCompte(1L);
    }

    @Test
    public void testSupprimerCompte() throws Exception {
        doNothing().when(compteService).supprimerCompte(1L);

        mockMvc.perform(delete("/api/comptes/1"))
                .andExpect(status().isOk());

        verify(compteService, times(1)).supprimerCompte(1L);
    }
}
