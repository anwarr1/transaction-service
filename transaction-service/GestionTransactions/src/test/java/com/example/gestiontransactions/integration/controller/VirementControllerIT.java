package com.example.gestiontransactions.integration.controller;

import com.example.gestiontransactions.controller.VirementController;
import com.example.gestiontransactions.dto.SMS;
import com.example.gestiontransactions.model.Virement;
import com.example.gestiontransactions.service.VirementService;
import com.example.gestiontransactions.enums.TypeTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;

public class VirementControllerIT {

    private MockMvc mockMvc;

    @Mock
    private VirementService virementService;

    @InjectMocks
    private VirementController virementController;

    private Virement virement;
    private SMS sms;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(virementController).build();

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
    public void testEffectuerVirement() throws Exception {
        // Mock the behavior of the VirementService's effectuerVirement method
        when(virementService.effectuerVirement(org.mockito.ArgumentMatchers.any(Virement.class), org.mockito.ArgumentMatchers.any(SMS.class)))
                .thenReturn(virement);

        mockMvc.perform(post("/api/virements")
                        .contentType("application/json")
                        .content("{\"montant\":500,\"typeTransaction\":\"VIREMENT\",\"idUser\":1,\"sms\":{\"phone\":\"+212648361147\",\"customerFirstName\":\"John\",\"customerLastName\":\"Doe\",\"amount\":1000.0,\"beneficiaryFirstName\":\"Jane\",\"beneficiaryLastName\":\"Smith\",\"sendRef\":true,\"ref\":\"REF123\",\"pin\":\"1234\"}}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.montant", is(500.0)))
                .andExpect(jsonPath("$.typeTransaction", is("VIREMENT")))
                .andExpect(jsonPath("$.idUser", is(1)));
    }
}