package com.example.gestiontransactions.unit.service;

import com.example.gestiontransactions.exception.InsufficientFundsException;
import com.example.gestiontransactions.exception.ResourceNotFoundException;
import com.example.gestiontransactions.model.Compte;
import com.example.gestiontransactions.model.PaiementEnLigne;
import com.example.gestiontransactions.repository.CompteRepository;
import com.example.gestiontransactions.repository.PaiementEnLigneRepository;
import com.example.gestiontransactions.service.PaiementEnLigneService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaiementEnLigneServiceTest {

    @Mock
    private PaiementEnLigneRepository paiementEnLigneRepository;

    @Mock
    private CompteRepository compteRepository;

    @InjectMocks
    private PaiementEnLigneService paiementEnLigneService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testEffectuerPaiement_Success() {
        // Arrange
        Compte compte = new Compte();
        compte.setId(1L);
        compte.setSolde(1000.0);

        PaiementEnLigne paiement = new PaiementEnLigne();
        paiement.setId(1L);
        paiement.setMontant(200.0);
        paiement.setCompte(compte);

        when(compteRepository.findById(1L)).thenReturn(Optional.of(compte));
        when(paiementEnLigneRepository.save(paiement)).thenReturn(paiement);

        // Act
        PaiementEnLigne result = paiementEnLigneService.effectuerPaiement(paiement);

        // Assert
        assertNotNull(result);
        assertEquals(800.0, compte.getSolde());
        verify(compteRepository, times(1)).save(compte);
        verify(paiementEnLigneRepository, times(1)).save(paiement);
    }

    @Test
    void testEffectuerPaiement_CompteNonTrouve() {
        // Arrange
        PaiementEnLigne paiement = new PaiementEnLigne();
        Compte compte = new Compte();
        compte.setId(1L);
        paiement.setCompte(compte);

        when(compteRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> paiementEnLigneService.effectuerPaiement(paiement));
        assertEquals("Compte non trouvé", exception.getMessage());
        verify(compteRepository, times(1)).findById(1L);
        verify(paiementEnLigneRepository, never()).save(any());
    }

    @Test
    void testEffectuerPaiement_SoldeInsuffisant() {
        // Arrange
        Compte compte = new Compte();
        compte.setId(1L);
        compte.setSolde(100.0);

        PaiementEnLigne paiement = new PaiementEnLigne();
        paiement.setMontant(200.0);
        paiement.setCompte(compte);

        when(compteRepository.findById(1L)).thenReturn(Optional.of(compte));

        // Act & Assert
        InsufficientFundsException exception = assertThrows(InsufficientFundsException.class,
                () -> paiementEnLigneService.effectuerPaiement(paiement));
        assertEquals("Solde insuffisant pour effectuer le paiement", exception.getMessage());
        verify(compteRepository, times(1)).findById(1L);
        verify(paiementEnLigneRepository, never()).save(any());
    }
}
