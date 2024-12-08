package com.example.gestiontransactions.unit.controller;

import com.example.gestiontransactions.controller.VirementController;
import com.example.gestiontransactions.model.Virement;
import com.example.gestiontransactions.service.VirementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class VirementControllerTest {

    @Mock
    private VirementService virementService;

    @InjectMocks
    private VirementController virementController;

    private Virement virement;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Configuration de base pour le virement
        virement = new Virement();
        virement.setId(1L);
        virement.setMontant(1000.0);
    }

    @Test
    public void testEffectuerVirement() {
        // Simuler le comportement du service avec Mockito
        when(virementService.effectuerVirement(virement)).thenReturn(virement);

        // Appel de la méthode via le contrôleur
        Virement result = virementController.effectuerVirement(virement);

        // Vérifier que la méthode du service a été appelée avec le bon paramètre
        verify(virementService, times(1)).effectuerVirement(virement);

        // Vérifier le résultat
        assertEquals(virement, result);
    }
}