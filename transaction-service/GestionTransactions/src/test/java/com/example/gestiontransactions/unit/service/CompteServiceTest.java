package com.example.gestiontransactions.unit.service;

import com.example.gestiontransactions.exception.ResourceNotFoundException;
import com.example.gestiontransactions.model.Compte;
import com.example.gestiontransactions.repository.CompteRepository;
import com.example.gestiontransactions.service.CompteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CompteServiceTest {

    @Mock
    private CompteRepository compteRepository;

    @InjectMocks
    private CompteService compteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreerCompte() {
        // Arrange
        Compte compte = new Compte();
        compte.setId(1L);
        compte.setSolde(100.0);
        compte.setDevise("EUR");
        compte.setIdUser(123);

        when(compteRepository.save(compte)).thenReturn(compte);

        // Act
        Compte createdCompte = compteService.creerCompte(compte);

        // Assert
        assertNotNull(createdCompte);
        assertEquals(1L, createdCompte.getId());
        verify(compteRepository, times(1)).save(compte);
    }

    @Test
    void testRecupererCompte() {
        // Arrange
        Compte compte = new Compte();
        compte.setId(1L);
        compte.setSolde(100.0);
        compte.setDevise("EUR");
        compte.setIdUser(123);

        when(compteRepository.findById(1L)).thenReturn(Optional.of(compte));

        // Act
        Compte retrievedCompte = compteService.recupererCompte(1L);

        // Assert
        assertNotNull(retrievedCompte);
        assertEquals(1L, retrievedCompte.getId());
        verify(compteRepository, times(1)).findById(1L);
    }

    @Test
    void testRecupererCompte_NotFound() {
        // Arrange
        when(compteRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> compteService.recupererCompte(1L));
        verify(compteRepository, times(1)).findById(1L);
    }

    @Test
    void testMettreAJourCompte() {
        // Arrange
        Compte existingCompte = new Compte();
        existingCompte.setId(1L);
        existingCompte.setSolde(100.0);
        existingCompte.setDevise("EUR");
        existingCompte.setIdUser(123);

        Compte updatedDetails = new Compte();
        updatedDetails.setSolde(200.0);
        updatedDetails.setDevise("USD");
        updatedDetails.setIdUser(456);

        when(compteRepository.findById(1L)).thenReturn(Optional.of(existingCompte));
        when(compteRepository.save(existingCompte)).thenReturn(existingCompte);

        // Act
        Compte updatedCompte = compteService.mettreAJourCompte(1L, updatedDetails);

        // Assert
        assertNotNull(updatedCompte);
        assertEquals(200.0, updatedCompte.getSolde());
        assertEquals("USD", updatedCompte.getDevise());
        assertEquals(456, updatedCompte.getIdUser());
        verify(compteRepository, times(1)).findById(1L);
        verify(compteRepository, times(1)).save(existingCompte);
    }

    @Test
    void testMettreAJourCompte_NotFound() {
        // Arrange
        when(compteRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> compteService.mettreAJourCompte(1L, new Compte()));
        verify(compteRepository, times(1)).findById(1L);
    }

    @Test
    void testSupprimerCompte() {
        // Arrange
        Compte compte = new Compte();
        compte.setId(1L);

        when(compteRepository.findById(1L)).thenReturn(Optional.of(compte));
        doNothing().when(compteRepository).delete(compte);

        // Act
        compteService.supprimerCompte(1L);

        // Assert
        verify(compteRepository, times(1)).findById(1L);
        verify(compteRepository, times(1)).delete(compte);
    }

    @Test
    void testSupprimerCompte_NotFound() {
        // Arrange
        when(compteRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> compteService.supprimerCompte(1L));
        verify(compteRepository, times(1)).findById(1L);
    }
}
