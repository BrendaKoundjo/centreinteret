package com.centreinteret.centreinteret.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddresseDTO {
    private Long id;
    private String pointDeRepare;
    private String quartier;
    private String ville;
    private String pays;
    private Double latitude;
    private Double longitude;
    private String adresseFormatee;
    private String placeId;
    private Long centreInteretId;

    // Getters and setters
}
