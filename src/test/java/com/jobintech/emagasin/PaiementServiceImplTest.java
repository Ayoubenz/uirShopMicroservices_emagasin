//package com.jobintech.emagasin;
//
//import com.jobintech.emagasin.Exception.CommandeException;
//import com.jobintech.emagasin.Exception.PaiementException;
//import com.jobintech.emagasin.dto.CommandeDto;
//import com.jobintech.emagasin.dto.PaiementDto;
//import com.jobintech.emagasin.entity.*;
//import com.jobintech.emagasin.repository.CommandeRepository;
//import com.jobintech.emagasin.repository.PaiementRepository;
//import com.jobintech.emagasin.service.facade.CommandeService;
//import com.jobintech.emagasin.service.impl.CommandeServiceImpl;
//import com.jobintech.emagasin.service.impl.PaiementProcessService;
//import com.jobintech.emagasin.service.impl.PaiementServiceImpl;
//import com.jobintech.emagasin.transformer.CommandeConverter;
//import com.jobintech.emagasin.transformer.PaiementConverter;
//import org.junit.Before;
//import org.junit.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.annotation.DirtiesContext;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
//public class PaiementServiceImplTest {
//
//    @Test
//    public void savePaiementTest() throws PaiementException {
//        PaiementConverter paiementConverter = mock(PaiementConverter.class);
//        PaiementRepository paiementRepository = mock(PaiementRepository.class);
//        PaiementServiceImpl paiementService = new PaiementServiceImpl(paiementRepository,paiementConverter);
//
//        PaiementDto paiementDto = new PaiementDto(new CommandeDto(2L,"C1", BigDecimal.valueOf(100),null,null,null,null, null),120.00, EtatPaiement.PAYE, MethodDePaiement.EN_LIGNE);
//        Paiement paiement = new Paiement(1,new Commande(2L,"C1", BigDecimal.valueOf(100),null,null,null),120.00, EtatPaiement.PAYE, MethodDePaiement.EN_LIGNE);
//        // Mock the behavior of paiementConverter and paiementRepository
//        when(paiementConverter.toEntity(paiementDto)).thenReturn(paiement);
//        // Act
//        ResponseEntity<HttpStatus> response = paiementService.save(paiementDto);
//        // Assert
//        assertEquals(HttpStatus.OK, response.getStatusCode(), "HTTP status should be OK");
//
//    }
//
//    @Test
//    public void getPaiementTest() throws PaiementException {
//        long id = 1;
//        PaiementDto paiementDto = new PaiementDto(new CommandeDto(2L,"C1", BigDecimal.valueOf(100),null,null,null,null, null),120.00, EtatPaiement.PAYE, MethodDePaiement.EN_LIGNE);
//        Paiement paiement = new Paiement(1,new Commande(2L,"C1", BigDecimal.valueOf(100),null,null,null),120.00, EtatPaiement.PAYE, MethodDePaiement.EN_LIGNE);
//
//        PaiementRepository paiementRepository = mock(PaiementRepository.class);
//        PaiementConverter paiementConverter = mock(PaiementConverter.class);
//        PaiementServiceImpl paiementService = new PaiementServiceImpl(paiementRepository,paiementConverter);
//
//        when(paiementRepository.findById(id)).thenReturn(Optional.of(paiement));
//        when(paiementConverter.toDto(paiement)).thenReturn(paiementDto);
//
//        ResponseEntity<PaiementDto> response = paiementService.findById(id);
//        assertEquals(HttpStatus.OK, response.getStatusCode(), "HTTP status should be OK");
//        assertEquals(paiementDto,response.getBody(),"Objet found :) ");
//    }
//
//    @Test
//    public void Paiementservice_getAll_ListOfPaiement(){
//        //arrange
//        List<Paiement> paiements = new ArrayList<>();
//        paiements.add(new Paiement(1,new Commande(),200,EtatPaiement.PAYE,MethodDePaiement.EN_LIGNE));
//        paiements.add(new Paiement(2,new Commande(),40,EtatPaiement.EN_ATTENTE,MethodDePaiement.A_LA_LIVRAISON));
//        paiements.add(new Paiement(3,new Commande(),230,EtatPaiement.ANNULE,MethodDePaiement.EN_LIGNE));
//
//        List<PaiementDto> paiementDtos = new ArrayList<>();
//        paiementDtos.add(new PaiementDto(new CommandeDto(2L,"C1", BigDecimal.valueOf(100),null,null,null,null, null),200.0,EtatPaiement.PAYE,MethodDePaiement.EN_LIGNE));
//        paiementDtos.add(new PaiementDto(new CommandeDto(2L,"C1", BigDecimal.valueOf(100),null,null,null,null, null),40.0,EtatPaiement.EN_ATTENTE,MethodDePaiement.A_LA_LIVRAISON));
//        paiementDtos.add(new PaiementDto(new CommandeDto(2L,"C1", BigDecimal.valueOf(100),null,null,null,null, null),230.0,EtatPaiement.ANNULE,MethodDePaiement.EN_LIGNE));
//
//        PaiementRepository paiementRepository = mock(PaiementRepository.class);
//        PaiementConverter paiementConverter = mock(PaiementConverter.class);
//        PaiementServiceImpl paiementService = new PaiementServiceImpl(paiementRepository,paiementConverter);
//
//        when(paiementRepository.findAll()).thenReturn(paiements);
//        when(paiementConverter.toDto(paiements)).thenReturn(paiementDtos);
//
//        //act
//        List<PaiementDto> paiementDtos1 = paiementService.findAll();
//
//        //assert
//        assertEquals(paiementDtos,paiementDtos1);
//    }
//
//    @Test
//    public void UpdatePaiementTest() throws PaiementException {
//        //arrange
//        long id = 3;
//        PaiementDto updatedPaiementDto = new PaiementDto(new CommandeDto(3L,"C2",BigDecimal.valueOf(10),LocalDateTime.now(),"client","Items",MethodDePaiement.EN_LIGNE, EtatLivraison.PENDING),Double.valueOf("100"),EtatPaiement.PAYE,MethodDePaiement.EN_LIGNE);
//        Paiement updatedPaiement = new Paiement(2L, new Commande(3L,"C2",BigDecimal.valueOf(10),LocalDateTime.now(),"client","Items"),Double.valueOf("100"),EtatPaiement.PAYE,MethodDePaiement.EN_LIGNE);
//        Paiement paiementDB = new Paiement(3L, new Commande(3L,"C2",BigDecimal.valueOf(10),LocalDateTime.now(),"client","Items"),Double.valueOf("100"),EtatPaiement.PAYE,MethodDePaiement.EN_LIGNE);
//
//        PaiementRepository paiementRepository = mock(PaiementRepository.class);
//        PaiementConverter paiementConverter = mock(PaiementConverter.class);
//        PaiementServiceImpl paiementService = new PaiementServiceImpl(paiementRepository,paiementConverter);
//
//        when(paiementRepository.findById(id)).thenReturn(Optional.of(paiementDB));
//        when(paiementConverter.toEntity(updatedPaiementDto)).thenReturn(updatedPaiement);
//
//        //act
//        ResponseEntity<HttpStatus> response = paiementService.update(updatedPaiementDto,id);
//
//        //assert
//        assertEquals(HttpStatus.OK,response.getStatusCode());
//    }
//
//    @Test
//    public void deletePaiementTest() throws PaiementException {
//        //arrange
//        long id= 4;
//        Paiement paiementDB = new Paiement(4L, new Commande(3L,"C2",BigDecimal.valueOf(10),LocalDateTime.now(),"client","Items"),Double.valueOf("100"),EtatPaiement.PAYE,MethodDePaiement.EN_LIGNE);
//
//        PaiementRepository paiementRepository = mock(PaiementRepository.class);
//        PaiementConverter paiementConverter = mock(PaiementConverter.class);
//        PaiementServiceImpl paiementService = new PaiementServiceImpl(paiementRepository,paiementConverter);
//
//        when(paiementRepository.findById(id)).thenReturn(Optional.of(paiementDB));
//
//        //act
//        ResponseEntity<HttpStatus> response = paiementService.delete(id);
//
//        //assert
//        assertEquals(HttpStatus.OK,response.getStatusCode());
//    }
//
//    @Test
//    public void commandeService_getAll_ListOfCommands(){
//        //arrange
//        List<Commande> commandes = new ArrayList<>();
//        commandes.add(new Commande(1L,"C1",BigDecimal.valueOf(100), LocalDateTime.now(),null,null));
//        commandes.add(new Commande(2L,"C2",BigDecimal.valueOf(200),LocalDateTime.now(),null,null));
//        commandes.add(new Commande(3L,"C3",BigDecimal.valueOf(300),LocalDateTime.now(),null,null));
//
//        List<CommandeDto> commandeDtos = new ArrayList<>();
//        commandeDtos.add(new CommandeDto(1L,"C1",BigDecimal.valueOf(100),LocalDateTime.now(),null,null,null, null));
//        commandeDtos.add(new CommandeDto(2L,"C2",BigDecimal.valueOf(200),LocalDateTime.now(),null,null,null, null));
//        commandeDtos.add(new CommandeDto(3L,"C3",BigDecimal.valueOf(300),LocalDateTime.now(),null,null, null, null));
//
//        CommandeRepository commandeRepository = mock(CommandeRepository.class);
//        CommandeConverter commandeConverter = mock(CommandeConverter.class);
//        CommandeServiceImpl commandeService = new CommandeServiceImpl(commandeConverter,commandeRepository);
//
//        when(commandeRepository.findAll()).thenReturn(commandes);
//        when(commandeConverter.toDto(commandes)).thenReturn(commandeDtos);
//
//        //act
//        ResponseEntity<List<CommandeDto>> response = commandeService.findAllCommandes();
//
//        //assert
//        assertEquals(commandeDtos,response.getBody());
//    }
//
//    CommandeServiceImpl commandeService;
//    PaiementServiceImpl paiementService;
//    PaiementProcessService paiementProcessService;
//
//    @Before
//    public void setUp() {
//        // Initialisez vos mocks
//        commandeService = mock(CommandeServiceImpl.class);
//        paiementService = mock(PaiementServiceImpl.class);
//
//        // Initialisez votre classe avec les mocks
//        paiementProcessService = new PaiementProcessService(commandeService, paiementService); // Remplacez par le nom de votre classe
//    }
//    @Test
//    public void PaiementProcess_Makepayment_HttpRequest() throws CommandeException, PaiementException {
//
//        // arrange
//        Long orderId = 1L;
//        double amount = 100.0;
//        EtatPaiement etatPaiement = EtatPaiement.EN_ATTENTE; // Remplacez par l'état approprié
//        CommandeDto commandeDto = new CommandeDto(2L,"C1",BigDecimal.valueOf(100),LocalDateTime.now(),null,null,null, null);
//
//        ResponseEntity<CommandeDto> mockedCommandeDtoResponse = new ResponseEntity<>(commandeDto, HttpStatus.OK);
//        when(commandeService.findCommandeByID(orderId)).thenReturn(mockedCommandeDtoResponse);
//        when(paiementService.save(any(PaiementDto.class))).thenReturn(new ResponseEntity<>(HttpStatus.OK));
//
//        //act
//        ResponseEntity<HttpStatus> result = paiementProcessService.makePayment(orderId, amount, etatPaiement);
//
//        // assert .
//        assertEquals(HttpStatus.OK, result.getStatusCode());
//
//    }
//}
