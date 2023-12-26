package com.jobintech.emagasin.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record CommandeDto2( String reference, BigDecimal totalPaye, LocalDateTime dateCommande, ClientDto client, List<CommandeItemDto2> commandeItemDtos) {
}
