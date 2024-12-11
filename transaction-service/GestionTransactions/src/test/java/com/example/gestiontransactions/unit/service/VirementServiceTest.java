package com.example.gestiontransactions.unit.service;

import com.example.gestiontransactions.dto.SMS;
import com.example.gestiontransactions.exception.ResourceNotFoundException;
import com.example.gestiontransactions.model.Compte;
import com.example.gestiontransactions.model.Virement;
import com.example.gestiontransactions.repository.CompteRepository;
import com.example.gestiontransactions.repository.VirementRepository;
import com.example.gestiontransactions.service.VirementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VirementServiceTest {

    @Mock
    private CompteRepository compteRepository;

    @Mock
    private VirementRepository virementRepository;

    @InjectMocks
    private VirementService virementService;

    private SMS sms;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize the SMS object
        sms = new SMS();
        sms.setPhone("+212648361147");
        sms.setCustomerFirstName("John");
        sms.setCustomerLastName("Doe");
        sms.setAmount(1000.0);
        sms.setBeneficiaryFirstName("Jane");
        sms.setBeneficiaryLastName("Smith");
        sms.setSendRef(true);
        sms.setRef("REF123");
        sms.setPin("1234");
    }

    @Test
    void effectuerVirement_Success() {
        // Arrange
        Compte expediteur = new Compte();
        expediteur.setId(1L);
        expediteur.setSolde(500.0);

        Compte destinataire = new Compte();
        destinataire.setId(2L);
        destinataire.setSolde(300.0);

        Virement virement = new Virement();
        virement.setMontant(100.0);
        virement.setExpediteur(expediteur);
        virement.setDestinataire(destinataire);

        when(compteRepository.findById(1L)).thenReturn(Optional.of(expediteur));
        when(compteRepository.findById(2L)).thenReturn(Optional.of(destinataire));
        when(virementRepository.save(virement)).thenReturn(virement);

        // Act
        Virement result = virementService.effectuerVirement(virement, sms);

        // Assert
        assertNotNull(result);
        assertEquals(400.0, expediteur.getSolde());
        assertEquals(400.0, destinataire.getSolde());

        verify(compteRepository, times(1)).findById(1L);
        verify(compteRepository, times(1)).findById(2L);
        verify(compteRepository, times(1)).save(expediteur);
        verify(compteRepository, times(1)).save(destinataire);
        verify(virementRepository, times(1)).save(virement);
    }

    @Test
    void effectuerVirement_CompteExpediteurNotFound() {
        // Arrange
        Virement virement = new Virement();
        Compte expediteur = new Compte();
        expediteur.setId(1L);
        Compte destinataire = new Compte();
        destinataire.setId(2L);
        virement.setExpediteur(expediteur);
        virement.setDestinataire(destinataire);

        when(compteRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> virementService.effectuerVirement(virement, sms));

        verify(compteRepository, times(1)).findById(1L);
        verify(compteRepository, never()).findById(2L);
        verify(virementRepository, never()).save(any());
    }

    @Test
    void effectuerVirement_CompteDestinataireNotFound() {
        // Arrange
        Compte expediteur = new Compte();
        expediteur.setId(1L);
        expediteur.setSolde(500.0);

        Compte destinataire = new Compte();
        destinataire.setId(2L);

        Virement virement = new Virement();
        virement.setMontant(100.0);
        virement.setExpediteur(expediteur);
        virement.setDestinataire(destinataire);

        when(compteRepository.findById(1L)).thenReturn(Optional.of(expediteur));
        when(compteRepository.findById(2L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> virementService.effectuerVirement(virement, sms));

        verify(compteRepository, times(1)).findById(1L);
        verify(compteRepository, times(1)).findById(2L);
        verify(virementRepository, never()).save(any());
    }
}