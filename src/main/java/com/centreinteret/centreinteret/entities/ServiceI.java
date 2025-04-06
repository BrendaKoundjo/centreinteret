package com.centreinteret.centreinteret.entities;
import jakarta.persistence.*;
import lombok.*;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "service")
public class ServiceI {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String intitule;
    private String description;
    private Double prix;
    private String image;
    private String nom;
    @ManyToOne
    @JoinColumn(name = "centre_interet_id") // Nom de la colonne dans la base de données
    private Centreinteret centreInteret;
}