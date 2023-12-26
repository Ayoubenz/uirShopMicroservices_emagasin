package com.jobintech.emagasin.transformer;

import com.jobintech.emagasin.dto.CommandeDto;
import com.jobintech.emagasin.entity.Commande;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class CommandeConverter extends AbstractConverter<Commande, CommandeDto> {

    @Autowired
    private CommandeItemConverter commandeItemConverter;

    @Getter
    private Boolean commandeItems;

    public CommandeConverter() {
        init(true);
    }

    @Override
    public Commande toEntity(CommandeDto dto) {
        if (dto == null) {
            return null;
        } else {
            Commande commande = new Commande();
            commande.setId(dto.id());
            commande.setReference(dto.reference());
            commande.setTotalPaye(dto.totalPaye());
            commande.setDateCommande(dto.dateCommande());
            commande.setClient(dto.client());
            commande.setCommandItems(dto.commandeItemDtos());
            commande.setMethodDePaiement(dto.methodDePaiement());
            commande.setEtatLivraison(dto.etatLivraison());
            commande.setEtatPaiement(dto.etatPaiement());
            return commande;
        }
    }

    @Override
    public CommandeDto toDto(Commande entity) {
        if (entity == null) {
            return null;
        } else {
            commandeItemConverter.init(true);
            commandeItemConverter.setCommande(false);
            CommandeDto commandeDto = new CommandeDto(
                    entity.getId(),
                    entity.getReference(),
                    entity.getTotalPaye(),
                    entity.getDateCommande(),
                    entity.getClient(),
                    entity.getCommandItems(),
                    entity.getMethodDePaiement(),
                    entity.getEtatLivraison(),
                    entity.getEtatPaiement()
            );
            commandeItemConverter.setCommande(true);
            return commandeDto;
        }
    }

    public void init(Boolean value) {
        commandeItems = value;
    }

    public void setCommandeItems(Boolean commandeItems) {
        this.commandeItems = commandeItems;
    }
}
