package com.jobintech.emagasin.dto;

import com.jobintech.emagasin.entity.EtatLivraison;

public record CommandeStatusDto(Long id, EtatLivraison status) {
}
