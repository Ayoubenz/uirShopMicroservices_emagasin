package com.jobintech.emagasin.dto;

import java.math.BigDecimal;

public record CommandeItemDto(Long id, int quantity, BigDecimal prix, String product) {
}
