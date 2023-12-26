package com.jobintech.emagasin.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobintech.emagasin.Exception.CommandeException;
import com.jobintech.emagasin.dto.CommandeDto;
import com.jobintech.emagasin.entity.*;
import com.jobintech.emagasin.repository.CommandeRepository;
import com.jobintech.emagasin.service.facade.CommandeService;
import com.jobintech.emagasin.transformer.CommandeConverter;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class CommandeServiceImpl implements CommandeService {


    public final CommandeConverter commandeConverter;
    private final CommandeRepository commandeRepository;

    public CommandeServiceImpl(CommandeConverter commandeConverter,CommandeRepository commandeRepository) {
        this.commandeConverter = commandeConverter;
        this.commandeRepository = commandeRepository;
    }


    public ResponseEntity<CommandeDto> findCommandeByID(Long id) throws CommandeException {
        Optional<Commande> commande = commandeRepository.findById(id);
        if (commande.isPresent()) {
            CommandeDto commandeDto = commandeConverter.toDto(commande.get());
            return new ResponseEntity<>(commandeDto, HttpStatus.OK);
        } else {
            throw new CommandeException("La commande n'existe pas");
        }
    }

    public ResponseEntity<List<CommandeDto>> findAllCommandes() {
        List<Commande> commandes = commandeRepository.findAll();
        List<CommandeDto> commandeDtos = commandeConverter.toDto(commandes);

        return new ResponseEntity<>(commandeDtos, HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> deleteCommandeByID(Long id) throws CommandeException {
        Optional<Commande> commande = commandeRepository.findById(id);
        if (commande.isPresent()) {
            commandeRepository.delete(commande.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            throw new CommandeException("La Commande n'existe pas");
        }
    }

    public ResponseEntity<HttpStatus> UpdateCommande(CommandeDto updatedCommandeDto, Long id) throws CommandeException {
        Commande updatedCommande = commandeConverter.toEntity(updatedCommandeDto);
        Optional<Commande> commande = commandeRepository.findById(id);

        if (commande.isPresent()) {
            Commande existingCommande = commande.get();
            existingCommande.setReference(updatedCommande.getReference());
            existingCommande.setClient(updatedCommande.getClient());
            existingCommande.setDateCommande(updatedCommande.getDateCommande());
            existingCommande.setCommandItems(updatedCommande.getCommandItems());
            existingCommande.setTotalPaye(updatedCommande.getTotalPaye());
            existingCommande.setMethodDePaiement(updatedCommande.getMethodDePaiement());
            existingCommande.setEtatLivraison(updatedCommande.getEtatLivraison());
            existingCommande.setEtatPaiement(updatedCommande.getEtatPaiement());
            commandeRepository.save(existingCommande);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            throw new CommandeException("Données erronées");
        }
    }

    public ResponseEntity<HttpStatus> SaveCommande(CommandeDto commandeDto) throws CommandeException {
        try {
            Commande commande = commandeConverter.toEntity(commandeDto);
            commandeRepository.save(commande);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            throw new CommandeException("Données erronées");
        }
    }

    @Override
    public ResponseEntity<HttpStatus> SaveAll(List<CommandeDto> commandeDtos) throws CommandeException {
        try {

            List<Commande>commandes = commandeConverter.toEntity(commandeDtos);
            commandeRepository.saveAll(commandes);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            throw new CommandeException("Données erronées");
        }
    }


    public Client extractClientFromCommande(Long commandeId) throws IOException {
        Commande commande = commandeRepository.findById(commandeId)
                .orElseThrow(() -> new EntityNotFoundException("Commande not found with ID: " + commandeId));
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(commande.getClient(), Client.class);
    }

    //saveAll
    public ResponseEntity<HttpStatus> SaveAllCommande(List<CommandeDto> commandeDtoList) throws CommandeException {
        try {
            List<Commande> commande = commandeConverter.toEntity(commandeDtoList);
            commandeRepository.saveAll(commande);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            throw new CommandeException("Données erronées");
        }
    }






}
