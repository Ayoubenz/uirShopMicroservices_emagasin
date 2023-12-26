package com.jobintech.emagasin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {

    private Long Id;
    private String fullName;
    private String email;
    private String address;
    private String phoneNumber;

    private List<Commande> commandes;


}
