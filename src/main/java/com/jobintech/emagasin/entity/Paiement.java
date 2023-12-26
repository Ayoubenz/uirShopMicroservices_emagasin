package com.jobintech.emagasin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Paiement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long paiementId;

    @OneToOne
    @JoinColumn(name = "commande_id")
    private Commande commande;
    private double montant;
    private  EtatPaiement etatPaiement;
    private MethodDePaiement methodDePaiement;

}
