package com.example.gestiontransactions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class GestionTransactionsApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestionTransactionsApplication.class, args);
    }

}
