package com.example.gestiontransactions.unit.controller;

import com.example.gestiontransactions.controller.PaiementEnLigneController;
import com.example.gestiontransactions.model.PaiementEnLigne;
import com.example.gestiontransactions.service.PaiementEnLigneService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PaiementEnLigneControllerTest {

    @Mock
    private PaiementEnLigneService paiementEnLigneService;

    @InjectMocks
    private PaiementEnLigneController paiementEnLigneController;

    private PaiementEnLigne paiementEnLigne;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialise les mocks
        paiementEnLigne = new PaiementEnLigne();
        paiementEnLigne.setFournisseur("Test Fournisseur");
        paiementEnLigne.setReferenceTransaction("TX123");
    }

    @Test
    public void testEffectuerPaiement() {
        // Simule le comportement du service
        when(paiementEnLigneService.effectuerPaiement(any())).thenReturn(paiementEnLigne);

        // Appel de la méthode dans le contrôleur
        paiementEnLigneController.effectuerPaiement(paiementEnLigne);

        // Vérification que le service a bien été appelé une fois
        verify(paiementEnLigneService, times(1)).effectuerPaiement(any());
    }
}
