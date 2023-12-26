package com.jobintech.emagasin.jms.sender;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class EtatStockSender {
    private final JmsTemplate jmsTemplate;

    public EtatStockSender(JmsTemplate jmsTemplate) {this.jmsTemplate = jmsTemplate;}

    public void sendEtatStock(String jsonCommand) {
        jmsTemplate.convertAndSend("stockqueue", jsonCommand);
    }
}
