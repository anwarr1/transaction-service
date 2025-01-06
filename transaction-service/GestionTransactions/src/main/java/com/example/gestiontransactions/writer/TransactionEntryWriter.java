//package com.example.gestiontransactions.writer;
//
//
//import java.util.List;
//
//import com.example.gestiontransactions.model.TransactionEntry;
//import com.example.gestiontransactions.repository.TransactionEntryRepository;
//import jakarta.transaction.Transactional;
//
//import org.springframework.batch.item.Chunk;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class TransactionEntryWriter implements ItemWriter<TransactionEntry> {
//
//    private TransactionEntryRepository repository;
//
//    @Autowired
//    public TransactionEntryWriter(TransactionEntryRepository repository) {
//        this.repository = repository;
//    }
//
//    @Override
//    @Transactional
//    public void write(List<? extends TransactionEntry> items) throws Exception {
//        items.forEach(item -> item.setStatus("NEW"));
//        repository.saveAll(items);
//    }
//
//    @Override
//    public void write(Chunk<? extends TransactionEntry> chunk) throws Exception {
//
//    }
//}