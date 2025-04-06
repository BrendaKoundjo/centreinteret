package com.centreinteret.centreinteret.dto;

import com.centreinteret.centreinteret.entities.Centreinteret;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CentreinteretDTO {
    private Long id;
    private String nom;
    private String description;
    private Long categorieId;
    private boolean actif;
    private boolean disponible;
    private String image;
    private List<AddresseDTO> adresses;

    public CentreinteretDTO(Centreinteret centre) {
    }


    // Nouveaux champs pour Google Maps
//    private Double latitude;
//    private Double longitude;
//    private String adresseFormatee;
//    private String placeId;
//    private String urlPhoto;
//    private String horaires;
//    private Double note;
//    private Integer nombreAvis;


}