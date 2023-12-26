package com.jobintech.emagasin.controller;

import com.jobintech.emagasin.Exception.CommandeException;
import com.jobintech.emagasin.dto.CommandeDto;
import com.jobintech.emagasin.entity.Client;
import com.jobintech.emagasin.service.impl.CommandeServiceImpl;
import com.jobintech.emagasin.service.impl.OrderProcessServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@Log4j2
public class CommandeController {

    private final CommandeServiceImpl commandeService;
    private final OrderProcessServiceImpl orderProcessService;

    public CommandeController(CommandeServiceImpl commandeService, OrderProcessServiceImpl orderProcessService) {
        this.commandeService = commandeService;
        this.orderProcessService = orderProcessService;
    }

    @GetMapping("/Commande/{id}")
    public ResponseEntity<CommandeDto> findCommandeByID(@PathVariable Long id) {
        try {
            return commandeService.findCommandeByID(id);
        } catch (CommandeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/All")
    public ResponseEntity<List<CommandeDto>> findAllCommandes() {
        return commandeService.findAllCommandes();
    }

    @DeleteMapping("/Delete/{id}")
    public ResponseEntity<HttpStatus> deleteCommandeByID(@PathVariable Long id) {
        try {
            return commandeService.deleteCommandeByID(id);
        } catch (CommandeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/Update/{id}")
    public ResponseEntity<HttpStatus> UpdateCommande(@RequestBody CommandeDto updatedCommandeDto, @PathVariable Long id) {
        try {
            return commandeService.UpdateCommande(updatedCommandeDto, id);
        } catch (CommandeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/Save")
    public ResponseEntity<HttpStatus> SaveCommande(@RequestBody CommandeDto commandeDto) {
        try {
            return commandeService.SaveCommande(commandeDto);
        } catch (CommandeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/SaveAll")
    public ResponseEntity<HttpStatus> SaveAll(@RequestBody List<CommandeDto> commandeDtos) {
        try {
            return commandeService.SaveAll(commandeDtos);
        } catch (CommandeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/getClient/{commandeId}")
    public ResponseEntity<Client> getClientFromCommande(@PathVariable Long commandeId) {
        try {
            Client client = commandeService.extractClientFromCommande(commandeId);
            return new ResponseEntity<>(client, HttpStatus.OK);
        } catch (EntityNotFoundException | IOException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
    }

    @PostMapping("/place-order")
    public ResponseEntity<Object> placeOrder(@RequestBody CommandeDto commandeDto) {

            try {
                orderProcessService.processOrder(
                        commandeDto.reference(),
                        commandeDto.totalPaye(),
                        commandeDto.dateCommande(),
                        commandeDto.client(),
                        commandeDto.commandeItemDtos(),
                        commandeDto.methodDePaiement()
                );

                // Return a CompletableFuture with a success response
                return ResponseEntity.status(HttpStatus.CREATED).body("Commande passée avec succès");
            } catch (Exception e) {
                log.error("Error processing order: {}", e.getMessage(), e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la commande: " + e.getMessage());
            }

    }



    @PostMapping("/Save-All")
    public ResponseEntity<HttpStatus> SaveAllCommande(@RequestBody List<CommandeDto> commandeDtolist) {
        try {
            return commandeService.SaveAllCommande(commandeDtolist);
        } catch (CommandeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }




}
