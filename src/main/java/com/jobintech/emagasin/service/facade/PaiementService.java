package com.jobintech.emagasin.service.facade;

import com.jobintech.emagasin.Exception.PaiementException;
import com.jobintech.emagasin.dto.PaiementDto;
import com.jobintech.emagasin.entity.Paiement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface PaiementService {
    List<PaiementDto> findAll();

    ResponseEntity<PaiementDto> findById(Long id) throws PaiementException;
    ResponseEntity<HttpStatus> save(PaiementDto paiementDto) throws PaiementException;
    ResponseEntity<HttpStatus> update(PaiementDto paiementDto, Long id) throws PaiementException;

    ResponseEntity<HttpStatus> delete(Long id) throws PaiementException;

    ResponseEntity<HttpStatus>  saveAll(List<PaiementDto> paiementDtos) throws PaiementException;
}
