package com.jobintech.emagasin.service.impl;

import com.jobintech.emagasin.Exception.CommandeException;
import com.jobintech.emagasin.Exception.PaiementException;
import com.jobintech.emagasin.dto.PaiementDto;
import com.jobintech.emagasin.entity.Commande;
import com.jobintech.emagasin.entity.EtatPaiement;
import com.jobintech.emagasin.entity.Paiement;
import com.jobintech.emagasin.repository.CommandeRepository;
import com.jobintech.emagasin.repository.PaiementRepository;
import com.jobintech.emagasin.service.facade.PaiementService;
import com.jobintech.emagasin.transformer.PaiementConverter;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaiementServiceImpl implements PaiementService {

    private final PaiementRepository paiementRepository;
    private final PaiementConverter paiementConverter;


    public PaiementServiceImpl(PaiementRepository paiementRepository, PaiementConverter paiementConverter) {
        this.paiementRepository = paiementRepository;
        this.paiementConverter = paiementConverter;
    }

    @Override
    public List<PaiementDto> findAll() {
        List<Paiement> paiements = paiementRepository.findAll();
        return paiementConverter.toDto(paiements);
    }
    public List<Paiement> findAll2() {
        List<Paiement> paiements = paiementRepository.findAll();
        return paiements;
    }

    @Override
    public ResponseEntity<PaiementDto> findById(Long id) throws PaiementException {
        Optional<Paiement> paiement = paiementRepository.findById(id);
        if (paiement.isPresent()) {
            PaiementDto paiementDto = paiementConverter.toDto(paiement.get());
            return new ResponseEntity<>(paiementDto, HttpStatus.OK);
        } else {
            throw new PaiementException("Paiement introuvable avec l'ID spécifié");
        }
    }

    @Override
    public ResponseEntity<HttpStatus> save(PaiementDto paiementDto) throws PaiementException {
        Paiement paiement = paiementConverter.toEntity(paiementDto);
        try {
            paiementRepository.save(paiement);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            throw new PaiementException("Erreur lors de l'enregistrement du paiement");
        }
    }

    @Override
    public ResponseEntity<HttpStatus> update(PaiementDto paiementDto, Long id) throws PaiementException {
        Paiement paiement = paiementConverter.toEntity(paiementDto);
        Optional<Paiement> paiementExist = paiementRepository.findById(id);
        if (paiementExist.isPresent()) {
            paiementExist.get().setPaiementId(paiement.getPaiementId());
            paiementExist.get().setCommande(paiement.getCommande());
            paiementExist.get().setMontant(paiement.getMontant());
            paiementExist.get().setEtatPaiement(paiement.getEtatPaiement());
            paiementExist.get().setMethodDePaiement(paiement.getMethodDePaiement());
            paiementRepository.save(paiementExist.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            throw new PaiementException("Paiement introuvable avec l'ID spécifié");
        }
    }

    @Override
    public ResponseEntity<HttpStatus> delete(Long id) throws PaiementException {
        Optional<Paiement> paiement = paiementRepository.findById(id);
        if (paiement.isPresent()) {
            paiementRepository.delete(paiement.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            throw new PaiementException("Paiement introuvable avec l'ID spécifié");
        }
    }


    @Override
    public ResponseEntity<HttpStatus> saveAll(List<PaiementDto> paiementDtos) throws PaiementException {

        if (paiementDtos != null && !paiementDtos.isEmpty()) {
            List<Paiement> paiements = paiementConverter.toEntity(paiementDtos);
            paiementRepository.saveAll(paiements);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else throw new PaiementException("Liste des paiements introuvable");
    }


}
