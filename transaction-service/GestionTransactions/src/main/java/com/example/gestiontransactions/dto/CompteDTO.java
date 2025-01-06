package com.example.gestiontransactions.dto;


import jakarta.persistence.Column;
import lombok.Data;

@Data
public class CompteDTO {
    private Long id;
    private Double solde;
    private Integer idUser;
    private String Bank;
    private String rib;
    private String devise;

    public CompteDTO(Long id, Double solde, Integer idUser, String Bank,String rib,String devise) {
        this.id = id;
        this.solde = solde;
        this.idUser = idUser;
        this.Bank = Bank;
        this.rib = rib;
        this.devise = devise;
    }
}