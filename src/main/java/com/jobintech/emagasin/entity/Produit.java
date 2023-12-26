package com.jobintech.emagasin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Produit {
    private Long id;
    private String reference;
    private String libelle;
    private String categorieProduitDto;
}
