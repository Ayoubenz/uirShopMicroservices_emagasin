package com.jobintech.emagasin.controller;

import com.jobintech.emagasin.Exception.CommandeException;
import com.jobintech.emagasin.Exception.PaiementException;
import com.jobintech.emagasin.dto.PaiementDto;
import com.jobintech.emagasin.entity.Paiement;
import com.jobintech.emagasin.service.facade.PaiementService;
import com.jobintech.emagasin.transformer.PaiementConverter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/paiements")
@Tag(name = "CRUD REST APIs for Paiements Resource")
public class PaiementController {

    private final PaiementService paiementService;
    private final PaiementConverter paiementConverter;

    public PaiementController(PaiementService paiementService, PaiementConverter paiementConverter) {
        this.paiementService = paiementService;
        this.paiementConverter = paiementConverter;
    }
    @PostMapping("/")
    public ResponseEntity<HttpStatus> save(@RequestBody PaiementDto paiementDto) {

        try {
            return paiementService.save(paiementDto);
        } catch (PaiementException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<PaiementDto> findById(@PathVariable Long id) {

        ResponseEntity<PaiementDto> responseEntity = null;
        try {
           responseEntity = paiementService.findById(id);

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                PaiementDto paiementDto = responseEntity.getBody();
                return new ResponseEntity<>(paiementDto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(responseEntity.getStatusCode());
            }
        }catch (PaiementException e)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody PaiementDto paiementDto, @PathVariable  Long id) {

       Paiement paiement = paiementConverter.toEntity(paiementDto);
        try {
       return paiementService.update(paiementDto, id);
        } catch (PaiementException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<HttpStatus> deleteCommandeByID(@PathVariable Long id){
        try {
            return paiementService.delete(id);
        } catch (PaiementException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
