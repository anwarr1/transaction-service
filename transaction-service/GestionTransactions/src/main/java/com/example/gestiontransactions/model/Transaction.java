package com.example.gestiontransactions.model;

import com.example.gestiontransactions.enums.StatutTransaction;
import com.example.gestiontransactions.enums.TypeTransaction;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double montant;
    private Date date;

    @Column(name = "user_id")
    private Integer idUser;

    @Enumerated(EnumType.STRING)
    private StatutTransaction statutTransaction;

    @Enumerated(EnumType.STRING)
    private TypeTransaction typeTransaction;

}
