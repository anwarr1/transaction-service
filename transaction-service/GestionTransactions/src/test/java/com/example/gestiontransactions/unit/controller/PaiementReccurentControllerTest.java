package com.example.gestiontransactions.unit.controller;

import com.example.gestiontransactions.controller.PaiementReccurentController;
import com.example.gestiontransactions.model.PaiementReccurent;
import com.example.gestiontransactions.service.PaiementReccurentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PaiementReccurentControllerTest {

    @Mock
    private PaiementReccurentService paiementReccurentService;

    @InjectMocks
    private PaiementReccurentController paiementReccurentController;

    private PaiementReccurent paiementReccurent;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        paiementReccurent = new PaiementReccurent();
        paiementReccurent.setId(1L);
        paiementReccurent.setFrequence(com.example.gestiontransactions.enums.FrequencePaiement.MENSUEL);
        paiementReccurent.setProchainePaiementDate(new java.util.Date());
    }

    @Test
    public void testPlanifierPaiement_UnitTest() {
        when(paiementReccurentService.planifierPaiement(any())).thenReturn(paiementReccurent);

        PaiementReccurent result = paiementReccurentController.planifierPaiement(paiementReccurent);

        assertEquals(com.example.gestiontransactions.enums.FrequencePaiement.MENSUEL, result.getFrequence());
        assertEquals(paiementReccurent.getProchainePaiementDate(), result.getProchainePaiementDate());
    }

    @Test
    public void testAnnulerPaiement_UnitTest() {
        paiementReccurentController.annulerPaiement(1L);

        verify(paiementReccurentService).annulerPaiement(1L);
    }
}
