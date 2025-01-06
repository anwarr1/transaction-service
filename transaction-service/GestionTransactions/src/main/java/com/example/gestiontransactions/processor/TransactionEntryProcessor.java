//package com.example.gestiontransactions.processor;
//
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//
//import com.example.gestiontransactions.model.Transaction;
//import com.example.gestiontransactions.model.TransactionEntry;
//
//
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.security.auth.login.AccountNotFoundException;
//
//@Component
//public class TransactionEntryProcessor implements ItemProcessor<TransactionEntry, Transaction> {
//
//    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//
//    @Autowired
//    private AccountService accountService;
//
//    @Override
//    public Transaction process(TransactionEntry item) throws Exception {
//        try {
//            accountService.get(item.getAccountNo());
//        }
//        catch (AccountNotFoundException ex) {
//            return null;
//        }
//        return Transaction.builder()
//                .account(Account.builder().accountNumber(item.getAccountNo()).build())
//                .transactionAmount(Double.valueOf(item.getAmount()))
//                .transactionType(TransactionType.valueOf(item.getTransactionType()))
//                .transactionDate(LocalDate.parse(item.getTransactionDate(), formatter)).build();
//    }
//
//}