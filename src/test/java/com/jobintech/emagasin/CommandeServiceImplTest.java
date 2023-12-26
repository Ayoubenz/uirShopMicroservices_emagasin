//package com.jobintech.emagasin;
//
//import com.google.common.base.Verify;
//import com.jobintech.emagasin.Exception.CommandeException;
//import com.jobintech.emagasin.dto.CommandeDto;
//import com.jobintech.emagasin.entity.Commande;
//import com.jobintech.emagasin.entity.EtatLivraison;
//import com.jobintech.emagasin.entity.MethodDePaiement;
//import com.jobintech.emagasin.repository.CommandeRepository;
//import com.jobintech.emagasin.service.impl.CommandeServiceImpl;
//import com.jobintech.emagasin.transformer.CommandeConverter;
//import org.junit.Test;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@RunWith(MockitoJUnitRunner.class)
//public class CommandeServiceImplTest {
//
//    @Mock
//    CommandeRepository commandeRepository;
//    @Mock
//    CommandeConverter commandeConverter;
//
//    @InjectMocks
//    CommandeServiceImpl commandeService;
//
//    private List<Commande> dbCommandes  = new ArrayList<>(Arrays.asList(
//           new Commande(1L,"Ref1", BigDecimal.valueOf(190), LocalDateTime.now(),"{Clien1}","{CommandeItems1}"),
//            new Commande(2L,"Ref2", BigDecimal.valueOf(300), LocalDateTime.now(),"{Clien2}","{CommandeItems2}"),
//            new Commande(3L,"Ref3", BigDecimal.valueOf(627), LocalDateTime.now(),"{Clien3}","{CommandeItems3}"),
//            new Commande(4L,"Ref4", BigDecimal.valueOf(283), LocalDateTime.now(),"{Clien4}","{CommandeItems4}")
//
//    ));
//
//    @Test
//    public void findCommandeByIDTest() throws CommandeException {
//         Long id = 1L;
//         Commande commande = new Commande(1L,"Ref1", BigDecimal.valueOf(190), LocalDateTime.now(),"{Clien1}","{CommandeItems1}");
//         CommandeDto commandeDto = new CommandeDto(1L,"Ref1", BigDecimal.valueOf(190), LocalDateTime.now(),"{Clien1}","{CommandeItems1}",MethodDePaiement.EN_LIGNE,EtatLivraison.PENDING);
//
//         when(commandeRepository.findById(id)).thenReturn(Optional.of(commande));
//         when(commandeConverter.toDto(commande)).thenReturn(commandeDto);
//
//        ResponseEntity<CommandeDto> response = commandeService.findCommandeByID(id);
//
//        assertEquals(commandeDto,response.getBody());
//    }
//
//    @Test
//    public void deleteCommandeByIDTes() throws CommandeException {
//        Long id = 1L;
//        Commande commande = new Commande(1L,"Ref1", BigDecimal.valueOf(190), LocalDateTime.now(),"{Clien1}","{CommandeItems1}");
//
//        when(commandeRepository.findById(id)).thenReturn(Optional.of(commande));
//
//        ResponseEntity<HttpStatus> response = commandeService.deleteCommandeByID(id);
//        verify(commandeRepository).delete(commande);
//        assertEquals(HttpStatus.OK,response.getStatusCode());
//    }
//
//    @Test
//    public void UpdateCommandeTest() throws CommandeException {
//        Long id = 1L;
//        CommandeDto commandeDto = new CommandeDto(10L,"Ref10", BigDecimal.valueOf(1210), LocalDateTime.now(),"{Client10}","{CommandeItems10}", MethodDePaiement.EN_LIGNE,EtatLivraison.PENDING);
//        Commande commande = new Commande(10L,"Ref10", BigDecimal.valueOf(1210), LocalDateTime.now(),"{Client10}","{CommandeItems10}");
//
//        when(commandeRepository.findById(id)).thenReturn(Optional.of(dbCommandes.get(0)));
//        when(commandeConverter.toEntity(commandeDto)).thenReturn(commande);
//
//        ResponseEntity<HttpStatus> response = commandeService.UpdateCommande(commandeDto,id);
//        verify(commandeRepository).save(dbCommandes.get(0));
//        assertEquals(HttpStatus.OK,response.getStatusCode());
//
//    }
//
//     @Test
//    public void SaveCommandeTest() throws CommandeException {
//        CommandeDto commandeDto = new CommandeDto(10L,"Ref10", BigDecimal.valueOf(1210), LocalDateTime.now(),"{Client10}","{CommandeItems10}",MethodDePaiement.EN_LIGNE,EtatLivraison.PENDING);
//        Commande commande = new Commande(10L,"Ref10", BigDecimal.valueOf(1210), LocalDateTime.now(),"{Client10}","{CommandeItems10}",MethodDePaiement);
//
//        when(commandeConverter.toEntity(commandeDto)).thenReturn(commande);
//
//        ResponseEntity<HttpStatus> response = commandeService.SaveCommande(commandeDto);
//
//         verify(commandeConverter).toEntity(commandeDto);
//         verify(commandeRepository).save(commande);
//        assertEquals(HttpStatus.OK,response.getStatusCode());
//    }
//        //test
//    @Test
//    public void SaveAllCommandeTest() throws CommandeException {
//         List<CommandeDto> commandeDtos  = new ArrayList<>(Arrays.asList(
//                new CommandeDto(1L,"Ref1", BigDecimal.valueOf(190), LocalDateTime.now(),"{Clien1}","{CommandeItems1}",MethodDePaiement.EN_LIGNE, EtatLivraison.PENDING),
//                new CommandeDto(2L,"Ref2", BigDecimal.valueOf(300), LocalDateTime.now(),"{Clien2}","{CommandeItems2}",MethodDePaiement.EN_LIGNE,EtatLivraison.PENDING)
//        ));
//        List<Commande> commandes  = new ArrayList<>(Arrays.asList(
//                new Commande(1L,"Ref1", BigDecimal.valueOf(190), LocalDateTime.now(),"{Clien1}","{CommandeItems1}"),
//                new Commande(2L,"Ref2", BigDecimal.valueOf(300), LocalDateTime.now(),"{Clien2}","{CommandeItems2}")
//        ));
//         when(commandeConverter.toEntity(commandeDtos)).thenReturn(commandes);
//
//        ResponseEntity<HttpStatus> response = commandeService.SaveAll(commandeDtos);
//
//        verify(commandeConverter).toEntity(commandeDtos);
//        verify(commandeRepository).saveAll(commandes);
//        assertEquals(HttpStatus.OK,response.getStatusCode());
//    }
//
//    @Test
//    public void findAllCommandesTest()
//    {
//         List<CommandeDto> CommandeDtos  = new ArrayList<>(Arrays.asList(
//                new CommandeDto(1L,"Ref1", BigDecimal.valueOf(190), LocalDateTime.now(),"{Clien1}","{CommandeItems1}",MethodDePaiement.EN_LIGNE,EtatLivraison.PENDING),
//                new CommandeDto(2L,"Ref2", BigDecimal.valueOf(300), LocalDateTime.now(),"{Clien2}","{CommandeItems2}",MethodDePaiement.EN_LIGNE,EtatLivraison.PENDING),
//                new CommandeDto(3L,"Ref3", BigDecimal.valueOf(627), LocalDateTime.now(),"{Clien3}","{CommandeItems3}",MethodDePaiement.EN_LIGNE,EtatLivraison.PENDING),
//                new CommandeDto(4L,"Ref4", BigDecimal.valueOf(283), LocalDateTime.now(),"{Clien4}","{CommandeItems4}",MethodDePaiement.EN_LIGNE, EtatLivraison.PENDING)
//
//        ));
//        when(commandeRepository.findAll()).thenReturn(dbCommandes);
//        when(commandeConverter.toDto(dbCommandes)).thenReturn(CommandeDtos);
//
//        ResponseEntity<List<CommandeDto>> response = commandeService.findAllCommandes();
//
//        assertEquals(HttpStatus.OK,response.getStatusCode());
//        assertEquals("Ref3", Objects.requireNonNull(response.getBody()).get(2).reference());
//    }
//}
