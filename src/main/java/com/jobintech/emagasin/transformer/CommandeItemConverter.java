package com.jobintech.emagasin.transformer;

import com.jobintech.emagasin.dto.CommandeItemDto;
import com.jobintech.emagasin.entity.CommandeItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommandeItemConverter extends AbstractConverter<CommandeItem, CommandeItemDto> {

    @Autowired
    private CommandeConverter commandeConverter;

    private boolean commande;

    public CommandeItemConverter() {
        init(true);
    }

    @Override
    public CommandeItem toEntity(CommandeItemDto dto) {
        if (dto == null) {
            return null;
        } else {
            CommandeItem commandeItem = new CommandeItem();
            commandeItem.setId(dto.id());
            commandeItem.setQuantity(dto.quantity());
            commandeItem.setPrix(dto.prix());
            commandeItem.setProduit(dto.product());
            return commandeItem;
        }
    }

    @Override
    public CommandeItemDto toDto(CommandeItem entity) {
        if (entity == null) {
            return null;
        } else {
            return new CommandeItemDto(
                    entity.getId(),
                    entity.getQuantity(),
                    entity.getPrix(),
                    entity.getProduit()
            );
        }
    }

    public void init(boolean value) {
        commande = value;
    }

    public boolean getCommande() {
        return commande;
    }

    public void setCommande(boolean commande) {
        this.commande = commande;
    }
}
