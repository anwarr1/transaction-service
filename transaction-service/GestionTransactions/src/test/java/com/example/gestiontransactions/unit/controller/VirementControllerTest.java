package com.example.gestiontransactions.unit.controller;

import com.example.gestiontransactions.controller.VirementController;
import com.example.gestiontransactions.dto.SMS;
import com.example.gestiontransactions.model.Virement;
import com.example.gestiontransactions.request.VirementRequest;
import com.example.gestiontransactions.service.VirementService;
import com.example.gestiontransactions.enums.TypeTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class VirementControllerTest {

    @Mock
    private VirementService virementService;

    @InjectMocks
    private VirementController virementController;

    private Virement virement;
    private SMS sms;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Set up a mock Virement object
        virement = new Virement();
        virement.setMontant(500.0);
        virement.setTypeTransaction(TypeTransaction.VIREMENT);
        virement.setIdUser(1);

        // Set up a mock SMS object
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
    public void testEffectuerVirement() {
        // Mock the behavior of the VirementService's effectuerVirement method
        when(virementService.effectuerVirement(virement, sms)).thenReturn(virement);
        VirementRequest virementRequest=new VirementRequest();
        virementRequest.setVirement(virement);
        virementRequest.setSms(sms);
        Virement result = virementController.effectuerVirement(virementRequest);

        // Verify the service method was called
        verify(virementService).effectuerVirement(virement, sms);

        // Assert the result
        assertEquals(virement, result);
    }
}