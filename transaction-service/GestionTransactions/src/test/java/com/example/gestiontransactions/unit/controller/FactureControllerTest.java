package com.example.gestiontransactions.unit.controller;

import com.example.gestiontransactions.controller.FactureController;
import com.example.gestiontransactions.enums.StatutFacture;
import com.example.gestiontransactions.model.Facture;
import com.example.gestiontransactions.service.FactureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FactureControllerTest {

    @InjectMocks
    private FactureController factureController;

    @Mock
    private FactureService factureService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreerFacture() {
        Facture facture = new Facture();
        facture.setId(1L);
        facture.setFournisseurId("123");
        facture.setMontant(1000.0);
        facture.setStatut(StatutFacture.EN_ATTENTE);
        facture.setDateLimite(new Date());

        when(factureService.creerFacture(any(Facture.class))).thenReturn(facture);

        Facture result = factureController.creerFacture(facture);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("123", result.getFournisseurId());
        verify(factureService, times(1)).creerFacture(any());
    }

    @Test
    public void testRecupererFacture() {
        Facture facture = new Facture();
        facture.setId(1L);
        facture.setFournisseurId("123");
        facture.setMontant(500.0);
        facture.setStatut(StatutFacture.PAYÉE);

        when(factureService.recupererFacture(1L)).thenReturn(facture);

        Facture result = factureController.recupererFacture(1L);
        assertNotNull(result);
        assertEquals(500.0, result.getMontant());
        assertEquals("123", result.getFournisseurId());
        verify(factureService, times(1)).recupererFacture(1L);
    }

    @Test
    public void testMettreAJourFacture() {
        Facture factureDetails = new Facture();
        factureDetails.setMontant(1200.0);
        factureDetails.setStatut(StatutFacture.EN_ATTENTE);

        when(factureService.mettreAJourFacture(1L, factureDetails)).thenReturn(factureDetails);

        Facture result = factureController.mettreAJourFacture(1L, factureDetails);
        assertNotNull(result);
        assertEquals(1200.0, result.getMontant());
        assertEquals(StatutFacture.EN_ATTENTE, result.getStatut());
        verify(factureService, times(1)).mettreAJourFacture(1L, factureDetails);
    }

    @Test
    public void testSupprimerFacture() {
        doNothing().when(factureService).supprimerFacture(1L);

        assertDoesNotThrow(() -> factureController.supprimerFacture(1L));
        verify(factureService, times(1)).supprimerFacture(1L);
    }

    @Test
    public void testMarquerFacturePayee() {
        Facture facture = new Facture();
        facture.setId(1L);
        facture.setStatut(StatutFacture.PAYÉE);

        when(factureService.marquerFacturePayee(1L)).thenReturn(facture);

        Facture result = factureController.marquerFacturePayee(1L);
        assertNotNull(result);
        assertEquals(StatutFacture.PAYÉE, result.getStatut());
        verify(factureService, times(1)).marquerFacturePayee(1L);
    }
}
