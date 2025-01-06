package com.example.gestiontransactions.repository;

import com.example.gestiontransactions.model.Transaction;
import com.example.gestiontransactions.model.TransactionAnalytics;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT t FROM Transaction t WHERE YEAR(t.date) = :year AND MONTH(t.date) = :month")
    List<Transaction> findByDateRange(@Param("year") int year, @Param("month") int month);
    @Query(value= "SELECT new com.example.gestiontransactions.model.TransactionAnalytics(t.typeTransaction, t.montant, t.date) FROM Transaction t WHERE t.idUser = :userId ORDER BY t.date DESC LIMIT 3")
    List<TransactionAnalytics> findrecent3(Integer  userId);

    @Query(value= "SELECT SUM(t.montant) FROM Transaction  t WHERE t.idUser = :userId")
    Double getSumTransaction(Integer userId);

    @Query(value= "SELECT SUM(t.montant) FROM Virement t WHERE t.destinataire.idUser = :userId")
    Double getSumVirement(Integer userId);

}
