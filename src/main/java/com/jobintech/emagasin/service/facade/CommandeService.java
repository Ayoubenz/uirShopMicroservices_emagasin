package com.jobintech.emagasin.service.facade;

import com.jobintech.emagasin.Exception.CommandeException;
import com.jobintech.emagasin.dto.CommandeDto;
import com.jobintech.emagasin.entity.Commande;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface CommandeService {
    public ResponseEntity<CommandeDto> findCommandeByID(Long id) throws CommandeException;
    public ResponseEntity<List<CommandeDto>> findAllCommandes() throws CommandeException;
    public ResponseEntity<HttpStatus> deleteCommandeByID(Long id) throws CommandeException;
    public ResponseEntity<HttpStatus> UpdateCommande(CommandeDto commandeDto,Long id) throws CommandeException;
    public ResponseEntity<HttpStatus> SaveCommande(CommandeDto commandeDto) throws CommandeException;
    public ResponseEntity<HttpStatus> SaveAll(List<CommandeDto> commandeDtos) throws CommandeException;
}
