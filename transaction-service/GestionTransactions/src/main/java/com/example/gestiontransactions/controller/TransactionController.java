package com.example.gestiontransactions.controller;

import com.example.gestiontransactions.model.Transaction;
import com.example.gestiontransactions.model.TransactionAnalytics;
import com.example.gestiontransactions.repository.TransactionRepository;
import com.example.gestiontransactions.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;
    private TransactionRepository transactionRepository;

    @PostMapping
    public Transaction traiterTransaction(@RequestBody Transaction transaction) {
        return transactionService.traiterTransaction(transaction);
    }

    @PutMapping("/{id}/valider")
    public Transaction validerTransaction(@PathVariable Long id) {
        return transactionService.validerTransaction(id);
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getTransactions(
            @RequestParam int year,
            @RequestParam int month) {
        List<Transaction> transactions = transactionService.getTransactionsByYearAndMonth(year, month);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/RecentTransaction/{id}")
    public List<TransactionAnalytics> getRecentTransaction(@PathVariable Integer id) {
        return transactionRepository.findrecent3(id);
    }

    @GetMapping("/SumTransaction/{id}")
    public Double getSumTransaction(@PathVariable Integer id) {
        return transactionRepository.getSumTransaction(id);
    }

    @GetMapping("/SumVirement/{id}")
    public Double getSumVirement(@PathVariable Integer id) {
        return transactionRepository.getSumVirement(id);
    }
}
