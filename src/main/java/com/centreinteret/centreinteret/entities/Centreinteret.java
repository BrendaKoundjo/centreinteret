package com.centreinteret.centreinteret.entities;


import jakarta.persistence.CascadeType;
import lombok.*;
import jakarta.persistence.*;

import java.util.List;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "centre_interet")
public class Centreinteret {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String description;
    private boolean actif;
    private boolean disponible;
    private String image;

    @OneToMany(mappedBy = "centreInteret", cascade = CascadeType.ALL)
    private List<Reservation> reservations;
  @OneToMany(mappedBy = "centreinteret", cascade = CascadeType.ALL)
   private List<Addresse> adresses;

    @OneToMany(mappedBy = "centreinteret", cascade = CascadeType.ALL)
    private List<Contact> contacts;

    @OneToMany(mappedBy = "centreInteret", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServiceI> services;

    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

}