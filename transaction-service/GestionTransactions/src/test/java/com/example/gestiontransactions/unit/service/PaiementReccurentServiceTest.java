package com.example.gestiontransactions.unit.service;

import com.example.gestiontransactions.exception.ResourceNotFoundException;
import com.example.gestiontransactions.model.Compte;
import com.example.gestiontransactions.model.PaiementReccurent;
import com.example.gestiontransactions.repository.CompteRepository;
import com.example.gestiontransactions.repository.PaiementReccurentRepository;
import com.example.gestiontransactions.service.PaiementReccurentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaiementReccurentServiceTest {

    @Mock
    private PaiementReccurentRepository paiementReccurentRepository;

    @Mock
    private CompteRepository compteRepository;

    @InjectMocks
    private PaiementReccurentService paiementReccurentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPlanifierPaiement_Success() {
        // Arrange
        Compte compte = new Compte();
        compte.setId(1L);

        PaiementReccurent paiement = new PaiementReccurent();
        paiement.setId(1L);
        paiement.setCompte(compte);

        when(compteRepository.findById(1L)).thenReturn(Optional.of(compte));
        when(paiementReccurentRepository.save(paiement)).thenReturn(paiement);

        // Act
        PaiementReccurent result = paiementReccurentService.planifierPaiement(paiement);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getProchainePaiementDate());
        verify(compteRepository, times(1)).findById(1L);
        verify(paiementReccurentRepository, times(1)).save(paiement);
    }

    @Test
    void testPlanifierPaiement_CompteNonTrouve() {
        // Arrange
        PaiementReccurent paiement = new PaiementReccurent();
        Compte compte = new Compte();
        compte.setId(1L);
        paiement.setCompte(compte);

        when(compteRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> paiementReccurentService.planifierPaiement(paiement));
        assertEquals("Compte non trouvé", exception.getMessage());
        verify(compteRepository, times(1)).findById(1L);
        verify(paiementReccurentRepository, never()).save(any());
    }

    @Test
    void testAnnulerPaiement_Success() {
        // Arrange
        PaiementReccurent paiement = new PaiementReccurent();
        paiement.setId(1L);

        when(paiementReccurentRepository.findById(1L)).thenReturn(Optional.of(paiement));

        // Act
        paiementReccurentService.annulerPaiement(1L);

        // Assert
        verify(paiementReccurentRepository, times(1)).findById(1L);
        verify(paiementReccurentRepository, times(1)).delete(paiement);
    }

    @Test
    void testAnnulerPaiement_PaiementNonTrouve() {
        // Arrange
        when(paiementReccurentRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> paiementReccurentService.annulerPaiement(1L));
        assertEquals("Paiement récurrent non trouvé", exception.getMessage());
        verify(paiementReccurentRepository, times(1)).findById(1L);
        verify(paiementReccurentRepository, never()).delete(any());
    }
}
