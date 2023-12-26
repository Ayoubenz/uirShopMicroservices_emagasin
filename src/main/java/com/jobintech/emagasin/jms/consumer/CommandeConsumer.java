package com.jobintech.emagasin.jms.consumer;

import com.jobintech.emagasin.Exception.CommandeException;
import com.jobintech.emagasin.dto.CommandeDto;
import com.jobintech.emagasin.dto.CommandeStatusDto;
import com.jobintech.emagasin.dto.CommandeStatusPaiementDto;
import com.jobintech.emagasin.entity.Commande;
import com.jobintech.emagasin.jms.sender.BillingSender;
import com.jobintech.emagasin.jms.sender.CommandeSender;
import com.jobintech.emagasin.repository.CommandeRepository;
import com.jobintech.emagasin.service.impl.CommandeServiceImpl;
import com.jobintech.emagasin.service.impl.ConvertObjectToJson;
import com.jobintech.emagasin.service.impl.OrderProcessServiceImpl;
import com.jobintech.emagasin.transformer.CommandeConverter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class CommandeConsumer {

    @Autowired
    private CommandeServiceImpl commandeService;
    @Autowired
    private ConvertObjectToJson convertObjectToJson;
    @Autowired
    private CommandeConverter commandeConverter;
    @Autowired
    private BillingSender billingSender;

//    //Erreur de la commande
//    @JmsListener(destination = "CommandeDeadFFQueue", containerFactory = "queueListenerContainerFactory", concurrency = "10")
//    public void handleCommandTopic(String jsonCommand) {
//        log.info("Message DLQ recu de la part de service de livraison : {}", jsonCommand);
//    }

    //Statut de la commande
     @JmsListener(destination = "CommandeStatus", containerFactory = "queueListenerContainerFactory")
    public void receiveShippingStatus(String status) {
        // Handle the message in the DLQ
         log.info("Notification de la part de service de livraison : {} ",status);

        CommandeStatusDto commandeStatusDto = convertObjectToJson.convertJsonToObject(status, CommandeStatusDto.class);
        ResponseEntity<CommandeDto> commandedto = null;
        try {
             commandedto = commandeService.findCommandeByID(commandeStatusDto.id());
        } catch (CommandeException e) {
            log.info("Commande introuvale part le id {}. Exception : {}",commandeStatusDto.id(),e);
        }
        Commande commande = commandeConverter.toEntity(commandedto.getBody());
        commande.setEtatLivraison(commandeStatusDto.status());
         try {
             commandeService.SaveCommande(commandeConverter.toDto(commande));
         } catch (CommandeException e) {
             log.info("Erreur! l'enregistrement de la commande est échoué",e);
         }
         log.info("L' état de livraison de la commande num :{} est désormais : {}",commande.getId(),commande.getEtatLivraison());
    }

    @JmsListener(destination = "CommandeStatusTopic", containerFactory = "topicListenerContainerFactory")
    public void receiveShippingAndPaymentStatus(String status) {
        // Handle the message in the DLQ
        log.info("Notification de la part de service de livraison : {} ",status);

        CommandeStatusPaiementDto commandeStatusPaiementDto = convertObjectToJson.convertJsonToObject(status, CommandeStatusPaiementDto.class);
        ResponseEntity<CommandeDto> commandedto = null;
        try {
            commandedto = commandeService.findCommandeByID(commandeStatusPaiementDto.id());
        } catch (CommandeException e) {
            log.error("Commande introuvale part le id {}. Exception : {}",commandeStatusPaiementDto.id(),e);
        }
        assert commandedto != null;
        Commande commande = commandeConverter.toEntity(commandedto.getBody());
        commande.setEtatLivraison(commandeStatusPaiementDto.status());
        commande.setEtatPaiement(commandeStatusPaiementDto.etatPaiement());
        try {
            commandeService.SaveCommande(commandeConverter.toDto(commande));

        } catch (CommandeException e) {
            log.error("Erreur! l'enregistrement de la commande est échoué",e);
        }
        log.info("L' état de livraison de la commande num :{} est désormais : {}",commande.getId(),commande.getEtatLivraison());
    }


}
