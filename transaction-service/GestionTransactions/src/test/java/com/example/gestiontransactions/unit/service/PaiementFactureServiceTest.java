package com.example.gestiontransactions.unit.service;

import com.example.gestiontransactions.enums.StatutFacture;
import com.example.gestiontransactions.exception.InsufficientFundsException;
import com.example.gestiontransactions.exception.ResourceNotFoundException;
import com.example.gestiontransactions.model.Compte;
import com.example.gestiontransactions.model.Facture;
import com.example.gestiontransactions.model.PaiementFacture;
import com.example.gestiontransactions.repository.CompteRepository;
import com.example.gestiontransactions.repository.FactureRepository;
import com.example.gestiontransactions.repository.PaiementFactureRepository;
import com.example.gestiontransactions.service.PaiementFactureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaiementFactureServiceTest {

    @Mock
    private PaiementFactureRepository paiementFactureRepository;

    @Mock
    private CompteRepository compteRepository;

    @Mock
    private FactureRepository factureRepository;

    @InjectMocks
    private PaiementFactureService paiementFactureService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testTraiterPaiement_Success() {
        // Arrange
        Compte compte = new Compte();
        compte.setId(1L);
        compte.setSolde(1000.0);

        Facture facture = new Facture();
        facture.setId(1L);
        facture.setStatut(StatutFacture.EN_ATTENTE);

        PaiementFacture paiement = new PaiementFacture();
        paiement.setId(1L);
        paiement.setMontant(200.0);
        paiement.setCompte(compte);

        when(compteRepository.findById(1L)).thenReturn(Optional.of(compte));
        when(factureRepository.findById(1L)).thenReturn(Optional.of(facture));
        when(paiementFactureRepository.save(paiement)).thenReturn(paiement);

        // Act
        PaiementFacture result = paiementFactureService.traiterPaiement(paiement);

        // Assert
        assertNotNull(result);
        assertEquals(800.0, compte.getSolde());
        assertEquals(StatutFacture.PAYÉE, facture.getStatut());
        verify(compteRepository, times(1)).save(compte);
        verify(factureRepository, times(1)).save(facture);
        verify(paiementFactureRepository, times(1)).save(paiement);
    }

    @Test
    void testTraiterPaiement_CompteNonTrouve() {
        // Arrange
        PaiementFacture paiement = new PaiementFacture();
        Compte compte = new Compte();
        compte.setId(1L);
        paiement.setCompte(compte);

        when(compteRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> paiementFactureService.traiterPaiement(paiement));
        assertEquals("Compte non trouvé", exception.getMessage());
        verify(compteRepository, times(1)).findById(1L);
        verify(factureRepository, never()).findById(any());
        verify(paiementFactureRepository, never()).save(any());
    }

    @Test
    void testTraiterPaiement_FactureNonTrouvee() {
        // Arrange
        Compte compte = new Compte();
        compte.setId(1L);
        compte.setSolde(1000.0);

        PaiementFacture paiement = new PaiementFacture();
        paiement.setId(1L);
        paiement.setMontant(200.0);
        paiement.setCompte(compte);

        when(compteRepository.findById(1L)).thenReturn(Optional.of(compte));
        when(factureRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> paiementFactureService.traiterPaiement(paiement));
        assertEquals("Facture non trouvée", exception.getMessage());
        verify(compteRepository, times(1)).findById(1L);
        verify(factureRepository, times(1)).findById(1L);
        verify(paiementFactureRepository, never()).save(any());
    }

    @Test
    void testTraiterPaiement_SoldeInsuffisant() {
        // Arrange
        Compte compte = new Compte();
        compte.setId(1L);
        compte.setSolde(100.0);

        Facture facture = new Facture();
        facture.setId(1L);
        facture.setStatut(StatutFacture.EN_ATTENTE);

        PaiementFacture paiement = new PaiementFacture();
        paiement.setMontant(200.0);
        paiement.setCompte(compte);

        when(compteRepository.findById(1L)).thenReturn(Optional.of(compte));
        when(factureRepository.findById(1L)).thenReturn(Optional.of(facture));

        // Act & Assert
        InsufficientFundsException exception = assertThrows(InsufficientFundsException.class,
                () -> paiementFactureService.traiterPaiement(paiement));
        assertEquals("Solde insuffisant pour effectuer le paiement", exception.getMessage());
        verify(compteRepository, times(1)).findById(1L);
        verify(factureRepository, never()).save(any());
        verify(paiementFactureRepository, never()).save(any());
    }
}
