//package com.example.gestiontransactions.writer;
//
//
//import java.util.List;
//
//import com.example.gestiontransactions.model.Transaction;
//import org.springframework.batch.item.Chunk;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class TransactionWriter implements ItemWriter<Transaction> {
//
//    @Autowired
//    private AccountService accountService;
//
//    @Override
//    public void write(List<? extends Transaction> items) throws Exception {
//        items.forEach(accountService::addTransaction);
//    }
//
//    @Override
//    public void write(Chunk<? extends Transaction> chunk) throws Exception {
//
//    }
//}