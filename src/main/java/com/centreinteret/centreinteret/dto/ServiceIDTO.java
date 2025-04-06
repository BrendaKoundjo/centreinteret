package com.centreinteret.centreinteret.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceIDTO {
    private Long id;
    private String intitule;
    private String description;
    private Double prix;
    private String image;
    private String nom;
    private Long centreInteretId;
}
