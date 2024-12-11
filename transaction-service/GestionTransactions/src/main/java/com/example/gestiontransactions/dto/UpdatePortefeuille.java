package com.example.gestiontransactions.dto;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
public class UpdatePortefeuille {
    private Long expediteurId;
    private Long destinataireId;
    private double somme;

    public Long getExpediteurId() {
        return expediteurId;
    }

    public void setExpediteurId(Long expediteurId) {
        this.expediteurId = expediteurId;
    }

    public Long getDestinataireId() {
        return destinataireId;
    }

    public void setDestinataireId(Long destinataireId) {
        this.destinataireId = destinataireId;
    }

    public double getSomme() {
        return somme;
    }

    public void setSomme(double somme) {
        this.somme = somme;
    }
}