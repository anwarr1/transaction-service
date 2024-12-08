package com.example.gestiontransactions.unit.controller;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Date;

public class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    private Transaction transaction;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Préparer un objet Transaction pour les tests
        transaction = new Transaction();
        transaction.setId(1L);
        transaction.setMontant(1000.0);
        transaction.setIdUser(123);
        transaction.setStatutTransaction(StatutTransaction.EN_ATTENTE);
        transaction.setTypeTransaction(TypeTransaction.ACHAT);
        transaction.setDate(new Date());
    }

    @Test
    public void testTraiterTransaction() {
        // Simuler le comportement du service avec Mockito
        when(transactionService.traiterTransaction(any())).thenReturn(transaction);

        // Appel du contrôleur
        Transaction response = transactionController.traiterTransaction(transaction);

        // Assertions pour vérifier la logique
        assertEquals(1000.0, response.getMontant());
        assertEquals(StatutTransaction.EN_ATTENTE, response.getStatutTransaction());
        assertEquals(TypeTransaction.ACHAT, response.getTypeTransaction());
    }

    @Test
    public void testValiderTransaction() {
        // Simuler le comportement du service avec Mockito
        when(transactionService.validerTransaction(1L)).thenReturn(transaction);

        // Appel du contrôleur
        Transaction response = transactionController.validerTransaction(1L);

        // Assertions pour vérifier la logique
        assertEquals(StatutTransaction.EN_ATTENTE, response.getStatutTransaction());
        assertEquals(TypeTransaction.ACHAT, response.getTypeTransaction());
        assertEquals(1000.0, response.getMontant());
    }
}
