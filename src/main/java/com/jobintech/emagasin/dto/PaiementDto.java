package com.jobintech.emagasin.dto;

import com.jobintech.emagasin.entity.EtatPaiement;
import com.jobintech.emagasin.entity.MethodDePaiement;

public record PaiementDto(CommandeDto commandeDto, Double montant, EtatPaiement etatPaiementDto,
                          MethodDePaiement methodDePaiement) {
}
