package com.jobintech.emagasin.dto;

import com.jobintech.emagasin.entity.EtatLivraison;
import com.jobintech.emagasin.entity.EtatPaiement;

public record CommandeStatusPaiementDto(Long id, EtatLivraison status, EtatPaiement etatPaiement)  {
}


