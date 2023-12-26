package com.jobintech.emagasin.dto;

import java.math.BigDecimal;

public record CommandeItemDto2(Long id, ProduitDto product, int quantity, BigDecimal prix) {
}
