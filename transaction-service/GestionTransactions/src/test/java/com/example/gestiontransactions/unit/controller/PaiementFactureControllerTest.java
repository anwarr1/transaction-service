//package com.example.gestiontransactions.unit.controller;
//
//import com.example.gestiontransactions.controller.PaiementFactureController;
//import com.example.gestiontransactions.model.*;
//import com.example.gestiontransactions.service.PaiementFactureService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//public class PaiementFactureControllerTest {
//
//    @Mock
//    private PaiementFactureService paiementFactureService;
//
//    @InjectMocks
//    private PaiementFactureController paiementFactureController;
//
//    private PaiementFacture paiementFacture;
//    private Fournisseur fournisseur;
//    private Facture facture;
//    private Compte compte;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//
//        // Mock related entities
//        fournisseur = new Fournisseur();
//        fournisseur.setId(1L);
//        fournisseur.setNom("Test Fournisseur");
//
//        facture = new Facture();
//        facture.setId(1L);
//        facture.setMontant(100.0);
//
//        compte = new Compte();
//        compte.setId(1L);
//        compte.setSolde(500.0);
//
//        // Initialize PaiementFacture
//        paiementFacture = new PaiementFacture();
//        paiementFacture.setFournisseur(fournisseur);
//        paiementFacture.setFacture(facture);
//        paiementFacture.setCompte(compte);
//
//        when(paiementFactureService.traiterPaiement(any())).thenReturn(paiementFacture);
//    }
//
//    @Test
//    public void testTraiterPaiement() {
//        // Call the controller method
//        PaiementFacture result = paiementFactureController.traiterPaiement(paiementFacture);
//
//        // Verify the response is correct
//        assertEquals(fournisseur.getNom(), result.getFournisseur().getNom());
//        assertEquals(facture.getMontant(), result.getFacture().getMontant());
//        assertEquals(compte.getId(), result.getCompte().getId());
//    }
//
//    @Test
//    public void testTraiterPaiement_ServiceInteraction() {
//        // Call the controller method with the mock
//        paiementFactureController.traiterPaiement(paiementFacture);
//
//        // Verify the service is called with the expected data
//        verify(paiementFactureService).traiterPaiement(any());
//    }
//}
