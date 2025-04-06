package com.centreinteret.centreinteret.entities;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categorie")
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private  String nom;
    @OneToMany(mappedBy = "categorie", cascade = CascadeType.ALL)
    private List<Centreinteret> centreInterets; // Liste des centres d'intérêt associés
}
