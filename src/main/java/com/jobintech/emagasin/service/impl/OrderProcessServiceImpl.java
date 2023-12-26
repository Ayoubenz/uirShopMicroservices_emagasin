package com.jobintech.emagasin.service.impl;

import com.jobintech.emagasin.Exception.CommandeException;
import com.jobintech.emagasin.Exception.PaiementException;
import com.jobintech.emagasin.dto.*;
import com.jobintech.emagasin.entity.*;
import com.jobintech.emagasin.jms.sender.CommandeSender;

import com.jobintech.emagasin.jms.sender.BillingSender;
import com.jobintech.emagasin.jms.sender.EtatStockSender;
import com.jobintech.emagasin.repository.CommandeRepository;
import com.jobintech.emagasin.service.facade.OrderProcessService;
import com.jobintech.emagasin.transformer.CommandeConverter;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Log4j2
@Service
public class OrderProcessServiceImpl implements OrderProcessService {


    @Value("${orders.inventory.check.rest}")
    private String checkInventory;
    @Autowired
    CommandeConverter commandeConverter;
    @Autowired
    CommandeSender commandeSender;
    @Autowired
    BillingSender billingSender;
    @Autowired
    EtatStockSender etatStockSender;
    @Autowired
    ConvertObjectToJson convertObjectToJson;
    @Autowired
    CommandeItem commandeItem;

    public final PaiementProcessService paiementProcessService;
    private final CommandeRepository commandeRepository;
    private final RestTemplate restTemplate;

    public OrderProcessServiceImpl(PaiementProcessService paiementProcessService, CommandeRepository commandeRepository, RestTemplate restTemplate) {
        this.paiementProcessService = paiementProcessService;
        this.commandeRepository = commandeRepository;
        this.restTemplate = restTemplate;
    }


    @Override
    @Transactional
    //<@Async("taskExecutor")
    public CompletableFuture<Void> processOrder(
            String reference,
            BigDecimal totalPaye,
            LocalDateTime dateCommande,
            String clientJson,
            String commandItemsJson,
            MethodDePaiement methodDePaiement
    ) {
      return  CompletableFuture.runAsync(() -> {
            try {
                List<CommandeItem> commandeItems = convertObjectToJson.convertJsonToList(commandItemsJson, CommandeItem.class);

                List<EtatStockDto> etatStockDtos = commandeItems.stream()
                        .map(cmd -> new EtatStockDto(cmd.getProduit(), cmd.getQuantity()))
                        .collect(Collectors.toList());

                Boolean areProductsAvailable = checkProductAvailability(etatStockDtos);

                if (!areProductsAvailable) {
                    log.error("Un ou plusieurs produits ne sont pas disponibles.");
                    throw new CommandeException("Un ou plusieurs produits ne sont pas disponibles.");
                }

                Commande commande = new Commande();
                //commande.setReference(reference);
                commande.setTotalPaye(totalPaye);
                commande.setDateCommande(dateCommande);
                commande.setClient(clientJson);
                commande.setCommandItems(commandItemsJson);
                commande.setMethodDePaiement(methodDePaiement);
                commande.setEtatLivraison(EtatLivraison.PENDING);
                commande.setEtatPaiement(EtatPaiement.EN_ATTENTE);

                commandeRepository.save(commande);
                log.info("La commande est enregistrée : {}", commande);
                Long orderId = commande.getId();

                CompletableFuture<Void> paymentFuture = CompletableFuture.runAsync(() -> {
                    try {
                        EtatPaiement etatPaiement = (methodDePaiement == MethodDePaiement.EN_LIGNE)
                                ? EtatPaiement.PAYE
                                : EtatPaiement.EN_ATTENTE;

                        paiementProcessService.makePayment(orderId, totalPaye.doubleValue(), etatPaiement, methodDePaiement);
                        commande.setEtatPaiement(etatPaiement);
                        commandeRepository.save(commande);
                        log.info("Le paiement est effectué pour la commande {} : {}", orderId, totalPaye);
                    } catch (PaiementException e) {
                        log.error("Erreur de paiement", e);
                    }
                });

                paymentFuture.exceptionally(ex -> {
                    // Handle exceptions thrown during asynchronous processing
                    if (ex.getCause() instanceof CommandeException) {
                        // Handle CommandeException
                        log.error("Erreur de commande", ex.getCause());
                    } else {
                        log.error("Unexpected error during asynchronous processing", ex.getCause());
                    }
                    return null;
                }).thenRun(() -> {
                    //Send for Delivery and Stock
                       commandeSender.sendCommand(convertObjectToJson.convertObjectToJson(commande));


                    //Send for Billing
                       billingSender.sendBiling(convertObjectToJson.convertObjectToJson(commande));


                    //  commandeSender.sendCommandTopic(convertObjectToJson.convertObjectToJson(commande));
                }).join(); // Wait for the paymentFuture to complete

            } catch (CommandeException e) {
                // Handle CommandeException
                log.error("Erreur de commande", e);
            }
        }).exceptionally(ex -> {
          // Handle exceptions thrown during the main CompletableFuture
          log.error("Erreur de commande", ex);
          return null;
      });
    }


    @Override
    public Boolean checkProductAvailability(List<EtatStockDto> itemForStock) {

        System.out.println(itemForStock);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Set up request entity with the list of EtatStockDtos
        HttpEntity<List<EtatStockDto>> requestEntity = new HttpEntity<>(itemForStock, headers);

        // Make the HTTP request to the ProductAvailabilityController endpoint
        ResponseEntity<Boolean> responseEntity = restTemplate.postForEntity(
                checkInventory,
                requestEntity,
                Boolean.class
        );

        // Extract the response body
        return responseEntity.getBody();
    }

}
