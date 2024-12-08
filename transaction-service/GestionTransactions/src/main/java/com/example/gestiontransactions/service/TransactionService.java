package com.example.gestiontransactions.service;

import com.example.gestiontransactions.enums.StatutTransaction;
import com.example.gestiontransactions.exception.ResourceNotFoundException;
import com.example.gestiontransactions.model.Transaction;
import com.example.gestiontransactions.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction traiterTransaction(Transaction transaction) {
        // Logique pour traiter la transaction
        return transactionRepository.save(transaction);
    }

    public Transaction validerTransaction(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction non trouvée"));
        transaction.setStatutTransaction(StatutTransaction.TERMINÉE); // Validation de la transaction
        return transactionRepository.save(transaction);
    }
}
