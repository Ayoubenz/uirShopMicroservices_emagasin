package com.jobintech.emagasin.service.facade;

import com.jobintech.emagasin.Exception.PaiementException;
import com.jobintech.emagasin.entity.EtatPaiement;
import com.jobintech.emagasin.entity.MethodDePaiement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface PaiementProcessService {
    public ResponseEntity<HttpStatus> makePayment(Long orderId, double amount, EtatPaiement etatPaiement, MethodDePaiement methodDePaiement) throws PaiementException;
    }
