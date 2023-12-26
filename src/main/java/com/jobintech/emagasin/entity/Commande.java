package com.jobintech.emagasin.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Entity
@Table(name = "commandes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String reference;
    private BigDecimal totalPaye;
    @CreationTimestamp
    private LocalDateTime dateCommande;
    @Column(length = 5000)
    private String client;
    @Column(length = 5000)
    private String commandItems;
    private MethodDePaiement methodDePaiement;
    private EtatLivraison etatLivraison;
    private EtatPaiement etatPaiement;



    @PrePersist
    public void generateReferenceAndDate() {
        // Generate a random alphanumeric string of length 8
        this.reference = "cmd-" + generateRandomString(8);

    }

    private String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder randomString = new StringBuilder();

        for (int i = 0; i < length; i++) {
            randomString.append(characters.charAt(new Random().nextInt(characters.length())));
        }

        return randomString.toString();
    }



}
