package com.jobintech.emagasin.inventoryService.service;

import com.jobintech.emagasin.Exception.ProductNotFoundException;
import com.jobintech.emagasin.dto.CommandeItemDto;
import com.jobintech.emagasin.dto.EtatStockDto;
import com.jobintech.emagasin.dto.ProduitDto;
import com.jobintech.emagasin.service.impl.ConvertObjectToJson;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductAvailabilityService {


    //I am stimulating the quanities of the products since I don't have their database's table

    @Autowired
    ConvertObjectToJson convertObjectToJson;
    private final Map<Long, Integer> productQuantitiesInStock = new HashMap<>();
    {
        productQuantitiesInStock.put(1L, 500);
        productQuantitiesInStock.put(2L, 500);
        productQuantitiesInStock.put(3L, 500);
        productQuantitiesInStock.put(4L, 500);
        productQuantitiesInStock.put(5L, 500);
    }

    public synchronized boolean checkAvailability(List<EtatStockDto> etatStockDtos) throws ProductNotFoundException {
        for (EtatStockDto etatStockDto : etatStockDtos) {
            ProduitDto produitDto = convertObjectToJson.convertJsonToObject(etatStockDto.product(),ProduitDto.class);

            Long productId = produitDto.id();

            if (productQuantitiesInStock.get(productId) == null) {
                throw new ProductNotFoundException("Produit introuvable : "+ produitDto.libelle());
            } else {
                int availableQuantity = productQuantitiesInStock.get(productId);
                if (availableQuantity < etatStockDto.quantity()) {
                    return false;
                }
            }
        }

        return true;
    }
}
