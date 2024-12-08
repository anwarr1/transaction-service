package com.example.gestiontransactions.unit.controller;

import com.example.gestiontransactions.controller.CompteController;
import com.example.gestiontransactions.model.Compte;
import com.example.gestiontransactions.service.CompteService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CompteControllerTest {

    @Mock
    private CompteService compteService;

    @InjectMocks
    private CompteController compteController;

    @Test
    public void testCreerCompte() {
        Compte compte = new Compte();
        compte.setId(1L);
        compte.setSolde(1000.0);
        compte.setDevise("USD");

        when(compteService.creerCompte(compte)).thenReturn(compte);

        Compte result = compteController.creerCompte(compte);

        assertNotNull(result);
        assertEquals(1000.0, result.getSolde());
        assertEquals("USD", result.getDevise());
        verify(compteService, times(1)).creerCompte(compte);
    }

    @Test
    public void testRecupererCompte() {
        Long idCompte = 1L;
        Compte compte = new Compte();
        compte.setId(idCompte);
        compte.setSolde(1000.0);
        compte.setDevise("EUR");

        when(compteService.recupererCompte(idCompte)).thenReturn(compte);

        Compte result = compteController.recupererCompte(idCompte);

        assertNotNull(result);
        assertEquals(1000.0, result.getSolde());
        assertEquals("EUR", result.getDevise());
        verify(compteService, times(1)).recupererCompte(idCompte);
    }

    @Test
    public void testMettreAJourCompte() {
        Long idCompte = 1L;
        Compte compteDetails = new Compte();
        compteDetails.setId(idCompte);
        compteDetails.setSolde(1500.0);
        compteDetails.setDevise("USD");

        when(compteService.mettreAJourCompte(idCompte, compteDetails)).thenReturn(compteDetails);

        Compte result = compteController.mettreAJourCompte(idCompte, compteDetails);

        assertNotNull(result);
        assertEquals(1500.0, result.getSolde());
        assertEquals("USD", result.getDevise());
        verify(compteService, times(1)).mettreAJourCompte(eq(idCompte), eq(compteDetails));
    }

    @Test
    public void testSupprimerCompte() {
        Long idCompte = 1L;
        doNothing().when(compteService).supprimerCompte(idCompte);

        assertDoesNotThrow(() -> compteController.supprimerCompte(idCompte));
        verify(compteService, times(1)).supprimerCompte(idCompte);
    }
}
