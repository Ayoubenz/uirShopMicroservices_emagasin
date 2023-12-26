package com.jobintech.emagasin.dto;

import com.jobintech.emagasin.entity.Produit;
import org.springframework.stereotype.Component;


public record EtatStockDto(String product, int quantity){
}
