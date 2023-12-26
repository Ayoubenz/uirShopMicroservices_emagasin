package com.jobintech.emagasin.service.impl;

import com.jobintech.emagasin.Exception.CommandeException;
import com.jobintech.emagasin.Exception.PaiementException;
import com.jobintech.emagasin.dto.CommandeDto;
import com.jobintech.emagasin.dto.PaiementDto;
import com.jobintech.emagasin.entity.Commande;
import com.jobintech.emagasin.entity.EtatPaiement;
import com.jobintech.emagasin.entity.MethodDePaiement;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class PaiementProcessService implements com.jobintech.emagasin.service.facade.PaiementProcessService {


    CommandeServiceImpl commandeService;
    PaiementServiceImpl paiementService;

    public PaiementProcessService(CommandeServiceImpl commandeService, PaiementServiceImpl paiementService) {
        this.commandeService = commandeService;
        this.paiementService = paiementService;
    }

    @Override
    public ResponseEntity<HttpStatus> makePayment(Long orderId, double amount, EtatPaiement etatPaiement, MethodDePaiement methodDePaiement) throws PaiementException {
        // Trouver la commande
        ResponseEntity<CommandeDto> commandedto = null;
        try {
            commandedto = commandeService.findCommandeByID(orderId);
        } catch (CommandeException e) {
            log.error("command not found :",e);
        }

        PaiementDto paiementDto = new PaiementDto(commandedto.getBody(), amount,etatPaiement, methodDePaiement);

        return paiementService.save(paiementDto);
    }
}
