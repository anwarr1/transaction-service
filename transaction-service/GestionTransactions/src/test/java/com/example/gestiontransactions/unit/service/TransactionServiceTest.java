package com.example.gestiontransactions.unit.service;

import com.example.gestiontransactions.enums.StatutTransaction;
import com.example.gestiontransactions.exception.ResourceNotFoundException;
import com.example.gestiontransactions.model.Transaction;
import com.example.gestiontransactions.repository.TransactionRepository;
import com.example.gestiontransactions.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testTraiterTransaction_Success() {
        // Arrange
        Transaction transaction = new Transaction();
        transaction.setId(1L);

        when(transactionRepository.save(transaction)).thenReturn(transaction);

        // Act
        Transaction result = transactionService.traiterTransaction(transaction);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(transactionRepository, times(1)).save(transaction);
    }

    @Test
    void testValiderTransaction_Success() {
        // Arrange
        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setStatutTransaction(StatutTransaction.EN_ATTENTE);

        when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction));
        when(transactionRepository.save(transaction)).thenReturn(transaction);

        // Act
        Transaction result = transactionService.validerTransaction(1L);

        // Assert
        assertNotNull(result);
        assertEquals(StatutTransaction.TERMINÉE, result.getStatutTransaction());
        verify(transactionRepository, times(1)).findById(1L);
        verify(transactionRepository, times(1)).save(transaction);
    }

    @Test
    void testValiderTransaction_TransactionNonTrouvee() {
        // Arrange
        when(transactionRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> transactionService.validerTransaction(1L));
        assertEquals("Transaction non trouvée", exception.getMessage());
        verify(transactionRepository, times(1)).findById(1L);
        verify(transactionRepository, never()).save(any());
    }
}
