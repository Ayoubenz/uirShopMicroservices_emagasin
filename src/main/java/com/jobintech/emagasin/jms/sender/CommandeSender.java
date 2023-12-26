package com.jobintech.emagasin.jms.sender;

import com.jobintech.emagasin.jms.consumer.CommandeConsumer;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.jms.Topic;
import java.util.concurrent.CompletableFuture;

@Component
@Log4j2
public class CommandeSender {
    private final JmsTemplate jmsTemplate;
    private final Topic commandTopic;

    public CommandeSender(JmsTemplate jmsTemplate, Topic commandTopic) {
        this.jmsTemplate = jmsTemplate;
        this.commandTopic = commandTopic;
    }


    public void sendCommand(String jsonCommand) {
            jmsTemplate.convertAndSend(commandTopic, jsonCommand);
            log.info("Les infos de la commande sont envoyées !!!!!!!!");


    }

//    public void sendCommandTopic(String jsonCommand) {
//        jmsTemplate.convertAndSend(commandTopic, jsonCommand);
//    }
}
