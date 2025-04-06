package com.centreinteret.centreinteret.entities;

import jakarta.persistence.*;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "addresse")
public class Addresse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pointDeRepare;
    private String quartier;
    private String ville;
    private String pays;
    private Double latitude;
    private Double longitude;
    private String adresseFormatee;
    private String placeId;

    @ManyToOne
    @JoinColumn(name = "centre_interet_id")
    private Centreinteret centreinteret;

    // Getters and setters
}