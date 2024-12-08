package com.example.gestiontransactions.integration.controller;

import com.example.gestiontransactions.controller.VirementController;
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

public class VirementControllerIT {

    private MockMvc mockMvc;

    @Mock
    private VirementService virementService;

    @InjectMocks
    private VirementController virementController;

    private Virement virement;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(virementController).build();

        // Set up a mock Virement object
        virement = new Virement();
        virement.setMontant(500.0);
        virement.setTypeTransaction(TypeTransaction.VIREMENT);
        virement.setIdUser(1);
    }

    @Test
    public void testEffectuerVirement() throws Exception {
        // Mock the behavior of the VirementService's effectuerVirement method
        when(virementService.effectuerVirement(org.mockito.ArgumentMatchers.any(Virement.class)))
                .thenReturn(virement);

        mockMvc.perform(post("/api/virements")
                        .contentType("application/json")
                        .content("{\"montant\":500,\"typeTransaction\":\"VIREMENT\",\"idUser\":1}"))
                .andExpect(status().isOk());
    }
}
