package com.example.gestiontransactions.controller;

import com.example.gestiontransactions.model.Transaction;
import com.example.gestiontransactions.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public Transaction traiterTransaction(@RequestBody Transaction transaction) {
        return transactionService.traiterTransaction(transaction);
    }

    @PutMapping("/{id}/valider")
    public Transaction validerTransaction(@PathVariable Long id) {
        return transactionService.validerTransaction(id);
    }
}
