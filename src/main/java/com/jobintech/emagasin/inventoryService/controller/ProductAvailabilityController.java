package com.jobintech.emagasin.inventoryService.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobintech.emagasin.Exception.ProductNotFoundException;
import com.jobintech.emagasin.dto.CommandeItemDto;
import com.jobintech.emagasin.dto.EtatStockDto;
import com.jobintech.emagasin.inventoryService.service.ProductAvailabilityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class ProductAvailabilityController {

    public final ProductAvailabilityService productAvailabilityService;

    public ProductAvailabilityController(ProductAvailabilityService productAvailabilityService) {
        this.productAvailabilityService = productAvailabilityService;
    }

    @PostMapping("/check-availability")
    public ResponseEntity<?> checkAvailability(@RequestBody List<EtatStockDto> etatStockDtos) throws JsonProcessingException {
        boolean isAvailable;
        System.out.println(new ObjectMapper().writeValueAsString(etatStockDtos));
        try {
            isAvailable = productAvailabilityService.checkAvailability(etatStockDtos);
            return ResponseEntity.ok(isAvailable);
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vous avez commandé un produit inexistant");

        }
    }
}
