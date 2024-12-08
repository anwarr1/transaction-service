package com.example.gestiontransactions.unit.controller;

import com.example.gestiontransactions.controller.PaiementFactureController;
import com.example.gestiontransactions.model.PaiementFacture;
import com.example.gestiontransactions.service.PaiementFactureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class PaiementFactureControllerTest {

    @Mock
    private PaiementFactureService paiementFactureService;

    @InjectMocks
    private PaiementFactureController paiementFactureController;

    private PaiementFacture paiementFacture;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        paiementFacture = new PaiementFacture();
        paiementFacture.setFournisseur("Test Fournisseur");
        paiementFacture.setMontant(100.0);

        when(paiementFactureService.traiterPaiement(any())).thenReturn(paiementFacture);
    }

    @Test
    public void testTraiterPaiement() {
        // Appel de la méthode du contrôleur
        PaiementFacture result = paiementFactureController.traiterPaiement(paiementFacture);

        // Vérification que la réponse est correcte
        assertEquals("Test Fournisseur", result.getFournisseur());
        assertEquals(100.0, result.getMontant());
    }

    @Test
    public void testTraiterPaiement_ServiceInteraction() {
        // Appel de la méthode du contrôleur avec un mock
        paiementFactureController.traiterPaiement(paiementFacture);

        // Vérification que le service est bien appelé avec les données attendues
        org.mockito.Mockito.verify(paiementFactureService).traiterPaiement(any());
    }
}
