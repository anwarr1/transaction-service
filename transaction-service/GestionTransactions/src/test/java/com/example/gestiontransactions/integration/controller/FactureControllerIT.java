//package com.example.gestiontransactions.integration.controller;
//
//import com.example.gestiontransactions.controller.FactureController;
//import com.example.gestiontransactions.enums.StatutFacture;
//import com.example.gestiontransactions.model.Facture;
//import com.example.gestiontransactions.service.FactureService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.util.Date;
//
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//public class FactureControllerIT {
//
//    private MockMvc mockMvc;
//
//    @Mock
//    private FactureService factureService;
//
//    @InjectMocks
//    private FactureController factureController;
//
//    private Facture facture;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//        this.mockMvc = MockMvcBuilders.standaloneSetup(factureController).build();
//
//        facture = new Facture();
//        facture.setId(1L);
//        facture.setFournisseurId("123");
//        facture.setMontant(1000.0);
//        facture.setStatut(StatutFacture.EN_ATTENTE);
//        facture.setDateLimite(new Date());
//    }
//
//    @Test
//    public void testCreerFacture() throws Exception {
//        // Configurer le mock
//        when(factureService.creerFacture(any())).thenReturn(facture);
//
//        // Effectuer la requête MockMvc
//        mockMvc.perform(post("/api/factures")
//                        .contentType("application/json")
//                        .content("{\"fournisseurId\":\"123\",\"montant\":1000.0,\"statut\":\"EN_ATTENTE\"}"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.fournisseurId").value("123"))
//                .andExpect(jsonPath("$.montant").value(1000.0));
//
//        // Vérifier que la méthode est bien appelée
//        verify(factureService, times(1)).creerFacture(any());
//    }
//
//    @Test
//    public void testSupprimerFacture() throws Exception {
//        doNothing().when(factureService).supprimerFacture(1L);
//
//        mockMvc.perform(delete("/api/factures/1"))
//                .andExpect(status().isOk());
//
//        verify(factureService, times(1)).supprimerFacture(1L);
//    }
//}
