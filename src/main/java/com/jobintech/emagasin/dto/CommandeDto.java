package com.jobintech.emagasin.dto;

import com.jobintech.emagasin.entity.EtatLivraison;
import com.jobintech.emagasin.entity.EtatPaiement;
import com.jobintech.emagasin.entity.MethodDePaiement;
import org.springframework.http.HttpStatusCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record CommandeDto(Long id, String reference, BigDecimal totalPaye, LocalDateTime dateCommande, String client, String commandeItemDtos,
                          MethodDePaiement methodDePaiement, EtatLivraison etatLivraison, EtatPaiement etatPaiement) {
}
