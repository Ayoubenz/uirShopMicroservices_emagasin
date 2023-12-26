package com.jobintech.emagasin.jms.sender;

import lombok.extern.log4j.Log4j2;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@Log4j2
public class BillingSender {


    private  final JmsTemplate jmsTemplate;

    public BillingSender(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendBiling(String commandeJson){

            jmsTemplate.convertAndSend("commandeBilling", commandeJson);
            log.info("Les infos de la commande sont envoyées au service de la facturation");

    }

//    public void sendBiling(List<FacturationDto> Biling, EtatPaiement etat){
//       jmsTemplate.convertAndSend("commandeBilling",Biling+"Etat paiement "+etat);
//    }
}
