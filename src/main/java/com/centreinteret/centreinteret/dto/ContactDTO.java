package com.centreinteret.centreinteret.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ContactDTO {
    private Long id;
    private String numeroWhatsapp;
    private String nom;
    private String email;
    private Long centreInteretId;

}
