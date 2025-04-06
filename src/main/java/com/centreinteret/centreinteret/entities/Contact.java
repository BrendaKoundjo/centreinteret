package com.centreinteret.centreinteret.entities;

import jakarta.persistence.*;
import lombok.*;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "contact")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String numeroWhatsapp; 
    private String email;

    @ManyToOne
    @JoinColumn(name = "centre_interet_id")
    private Centreinteret centreinteret;

}