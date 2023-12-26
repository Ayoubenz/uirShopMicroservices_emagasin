//package com.jobintech.emagasin;
//
//import com.jobintech.emagasin.Exception.PaiementException;
//import com.jobintech.emagasin.dto.EtatStockDto;
//import com.jobintech.emagasin.entity.Commande;
//import com.jobintech.emagasin.entity.EtatPaiement;
//import com.jobintech.emagasin.repository.CommandeRepository;
//import com.jobintech.emagasin.service.impl.OrderProcessServiceImpl;
//import com.jobintech.emagasin.service.impl.PaiementProcessService;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.client.RestTemplate;
//
//import java.lang.reflect.Array;
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.mockito.Mockito.*;
//
//@RunWith(MockitoJUnitRunner.class)
//public class OrderProcessServiceImplTest {
//
//
//    private PaiementProcessService paiementProcessService;
//
//    private CommandeRepository commandeRepository;
//
//    private OrderProcessServiceImpl orderProcessService;
//
//    private RestTemplate restTemplate;
//
//    @Before
//    public void setUp() {
//        paiementProcessService = mock(PaiementProcessService.class);
//        commandeRepository = mock(CommandeRepository.class);
//        restTemplate = mock(RestTemplate.class);
//
//        orderProcessService = new OrderProcessServiceImpl(paiementProcessService,commandeRepository, restTemplate);
//    }
//
//
////    @Test
////    public void processOrderTest() throws PaiementException {
////        String reference = "Ref123";
////        BigDecimal totalPaye = BigDecimal.valueOf(100.00);
////        LocalDateTime dateCommande = LocalDateTime.now();
////        String clientJson = "{ \"name\": \"John Doe\" }";
////        String commandItemsJson = "[{\"produit\": \"Product1\", \"quantity\": 2}]";
////
////        Commande commande = new Commande();
////        commande.setReference(reference);
////        commande.setTotalPaye(totalPaye);
////        commande.setDateCommande(dateCommande);
////        commande.setClient(clientJson);
////        commande.setCommandItems(commandItemsJson);
////
////        List<EtatStockDto> etatStockDtos = new ArrayList<>(Arrays.asList(
////                new EtatStockDto(null,10),
////                new EtatStockDto(null,134)
////        ));
////
////        when(orderProcessService.checkProductAvailability(anyList())).thenReturn(true);
////        orderProcessService.processOrder(reference, totalPaye, dateCommande, clientJson, commandItemsJson);
////
////        verify(commandeRepository).save(any(Commande.class));
////        verify(paiementProcessService).makePayment(commande.getId(), totalPaye.doubleValue(), EtatPaiement.PAYE);
////
////
////    }
//}
