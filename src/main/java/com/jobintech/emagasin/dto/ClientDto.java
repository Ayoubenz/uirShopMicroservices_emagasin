package com.jobintech.emagasin.dto;

import com.jobintech.emagasin.entity.Commande;

import java.util.List;

public record ClientDto(String fullName, String email,String address,String phoneNumber) {

}
