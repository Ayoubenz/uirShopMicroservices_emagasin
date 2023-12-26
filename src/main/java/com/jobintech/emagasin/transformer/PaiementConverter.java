package com.jobintech.emagasin.transformer;

import com.jobintech.emagasin.dto.PaiementDto;
import com.jobintech.emagasin.entity.Paiement;
import org.springframework.stereotype.Component;

@Component
public class PaiementConverter extends AbstractConverter<Paiement, PaiementDto>{


    private CommandeConverter commandeConverter;

    public PaiementConverter(CommandeConverter commandeConverter) {
        this.commandeConverter = commandeConverter;
    }

    public void setCommandeConverter(CommandeConverter commandeConverter) {
        this.commandeConverter = commandeConverter;
    }

    @Override
    public Paiement toEntity(PaiementDto dto) {
        if (dto == null)
            return null;
        else{
            Paiement paiement = new Paiement();
            paiement.setMontant(dto.montant());
            paiement.setMethodDePaiement(dto.methodDePaiement());
            paiement.setCommande(commandeConverter.toEntity(dto.commandeDto()));
            paiement.setEtatPaiement(dto.etatPaiementDto());
            return paiement;
        }

    }

    @Override
    public PaiementDto toDto(Paiement entity) {
        if (entity == null) {
            return null;
        }
        {
            return new PaiementDto(
                    commandeConverter.toDto(entity.getCommande()),
                    entity.getMontant(),
                    entity.getEtatPaiement(),
                    entity.getMethodDePaiement()
            );
        }
    }
}
