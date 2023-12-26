package com.jobintech.emagasin.service.facade;

import com.jobintech.emagasin.Exception.CommandeException;
import com.jobintech.emagasin.dto.EtatStockDto;
import com.jobintech.emagasin.entity.EtatLivraison;
import com.jobintech.emagasin.entity.EtatPaiement;
import com.jobintech.emagasin.entity.MethodDePaiement;
import com.jobintech.emagasin.repository.CommandeRepository;
import com.jobintech.emagasin.service.impl.PaiementProcessService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface OrderProcessService {
    public CompletableFuture<Void> processOrder(
            String reference,
            BigDecimal totalPaye,
            LocalDateTime dateCommande,
            String clientJson,
            String commandItemsJson,
            MethodDePaiement methodDePaiement
    ) throws CommandeException;
    Boolean checkProductAvailability(List<EtatStockDto> itemForStock);
//    public void effectuerCommande(
//            String reference,
//            BigDecimal total,
//            LocalDateTime dateCommande,
//            String client,
//            String commandeItems
//    );
}
