package com.example.gestiontransactions.unit.service;

import com.example.gestiontransactions.enums.StatutFacture;
import com.example.gestiontransactions.exception.ResourceNotFoundException;
import com.example.gestiontransactions.model.Facture;
import com.example.gestiontransactions.repository.FactureRepository;
import com.example.gestiontransactions.service.FactureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FactureServiceTest {

    @Mock
    private FactureRepository factureRepository;

    @InjectMocks
    private FactureService factureService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreerFacture() {
        // Arrange
        Facture facture = new Facture();
        facture.setId(1L);
        facture.setFournisseurId("100");
        facture.setMontant(500.0);
        facture.setDateLimite(Date.from(LocalDate.now().plusDays(60).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        facture.setStatut(StatutFacture.EN_ATTENTE);

        when(factureRepository.save(facture)).thenReturn(facture);

        // Act
        Facture createdFacture = factureService.creerFacture(facture);

        // Assert
        assertNotNull(createdFacture);
        assertEquals(1L, createdFacture.getId());
        verify(factureRepository, times(1)).save(facture);
    }

    @Test
    void testRecupererFacture() {
        // Arrange
        Facture facture = new Facture();
        facture.setId(1L);
        facture.setFournisseurId("100");

        when(factureRepository.findById(1L)).thenReturn(Optional.of(facture));

        // Act
        Facture retrievedFacture = factureService.recupererFacture(1L);

        // Assert
        assertNotNull(retrievedFacture);
        assertEquals(1L, retrievedFacture.getId());
        verify(factureRepository, times(1)).findById(1L);
    }

    @Test
    void testRecupererFacture_NotFound() {
        // Arrange
        when(factureRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> factureService.recupererFacture(1L));
        verify(factureRepository, times(1)).findById(1L);
    }

    @Test
    void testMettreAJourFacture() {
        // Arrange
        Facture existingFacture = new Facture();
        existingFacture.setId(1L);
        existingFacture.setFournisseurId("100");
        existingFacture.setMontant(500.0);
        existingFacture.setDateLimite(new Date());

        Facture updatedDetails = new Facture();
        updatedDetails.setFournisseurId("200");
        updatedDetails.setMontant(600.0);
        updatedDetails.setDateLimite(Date.from(LocalDate.now().plusDays(60).atStartOfDay(ZoneId.systemDefault()).toInstant()));

        when(factureRepository.findById(1L)).thenReturn(Optional.of(existingFacture));
        when(factureRepository.save(existingFacture)).thenReturn(existingFacture);

        // Act
        Facture updatedFacture = factureService.mettreAJourFacture(1L, updatedDetails);

        // Assert
        assertNotNull(updatedFacture);
        assertEquals(600.0, updatedFacture.getMontant());
        assertEquals("200", updatedFacture.getFournisseurId()); // Use String comparison
        verify(factureRepository, times(1)).findById(1L);
        verify(factureRepository, times(1)).save(existingFacture);
    }


    @Test
    void testMettreAJourFacture_NotFound() {
        // Arrange
        when(factureRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> factureService.mettreAJourFacture(1L, new Facture()));
        verify(factureRepository, times(1)).findById(1L);
    }

    @Test
    void testSupprimerFacture() {
        // Arrange
        Facture facture = new Facture();
        facture.setId(1L);

        when(factureRepository.findById(1L)).thenReturn(Optional.of(facture));
        doNothing().when(factureRepository).delete(facture);

        // Act
        factureService.supprimerFacture(1L);

        // Assert
        verify(factureRepository, times(1)).findById(1L);
        verify(factureRepository, times(1)).delete(facture);
    }

    @Test
    void testSupprimerFacture_NotFound() {
        // Arrange
        when(factureRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> factureService.supprimerFacture(1L));
        verify(factureRepository, times(1)).findById(1L);
    }

    @Test
    void testMarquerFacturePayee() {
        // Arrange
        Facture facture = new Facture();
        facture.setId(1L);
        facture.setStatut(StatutFacture.EN_ATTENTE);

        when(factureRepository.findById(1L)).thenReturn(Optional.of(facture));
        when(factureRepository.save(facture)).thenReturn(facture);

        // Act
        Facture updatedFacture = factureService.marquerFacturePayee(1L);

        // Assert
        assertNotNull(updatedFacture);
        assertEquals(StatutFacture.PAYÉE, updatedFacture.getStatut());
        verify(factureRepository, times(1)).findById(1L);
        verify(factureRepository, times(1)).save(facture);
    }

    @Test
    void testMarquerFacturePayee_NotFound() {
        // Arrange
        when(factureRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> factureService.marquerFacturePayee(1L));
        verify(factureRepository, times(1)).findById(1L);
    }
}
