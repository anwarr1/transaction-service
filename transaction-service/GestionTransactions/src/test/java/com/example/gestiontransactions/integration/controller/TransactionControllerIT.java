package com.example.gestiontransactions.integration.controller;

import com.example.gestiontransactions.controller.TransactionController;
import com.example.gestiontransactions.model.Transaction;
import com.example.gestiontransactions.service.TransactionService;
import com.example.gestiontransactions.enums.StatutTransaction;
import com.example.gestiontransactions.enums.TypeTransaction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.Mockito.when;

import java.util.Date;

public class TransactionControllerIT {

    private MockMvc mockMvc;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    private Transaction transaction;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();

        transaction = new Transaction();
        transaction.setId(1L);
        transaction.setMontant(1000.0);
        transaction.setIdUser(123);
        transaction.setStatutTransaction(StatutTransaction.EN_ATTENTE);
        transaction.setTypeTransaction(TypeTransaction.ACHAT);
        transaction.setDate(new Date());
    }

    @Test
    public void testTraiterTransaction() throws Exception {
        // Simuler le comportement du service
        when(transactionService.traiterTransaction(org.mockito.ArgumentMatchers.any()))
                .thenReturn(transaction);

        mockMvc.perform(post("/api/transactions")
                        .contentType("application/json")
                        .content("{\"montant\":1000,\"idUser\":123,\"statutTransaction\":\"EN_ATTENTE\",\"typeTransaction\":\"ACHAT\",\"date\":\"2024-12-08\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.montant").value(1000.0))
                .andExpect(jsonPath("$.statutTransaction").value("EN_ATTENTE"));
    }

    @Test
    public void testValiderTransaction() throws Exception {
        // Simuler le comportement du service
        when(transactionService.validerTransaction(1L)).thenReturn(transaction);

        mockMvc.perform(put("/api/transactions/1/valider"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statutTransaction").value("EN_ATTENTE"));
    }
}
