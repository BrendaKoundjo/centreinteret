package com.centreinteret.centreinteret.services;

import com.centreinteret.centreinteret.dto.CategorieDTO;
import com.centreinteret.centreinteret.dto.CentreinteretDTO;
import com.centreinteret.centreinteret.dto.ReservationDTO;

import java.util.List;

public interface centreInteretService {
    List<CentreinteretDTO> listeCentre();
    CentreinteretDTO recupererParId(Long id);
    CategorieDTO recupererCategorieParId(Long id);

   // List<CentreinteretDTO> rechercherParProximite(Double latitude, Double longitude, Double rayon);
    CentreinteretDTO ajouterCentreInteret(CentreinteretDTO centreinteretDTO);
    CentreinteretDTO mettreAJourCentreInteret(Long id, CentreinteretDTO centreinteretDTO);
    void supprimerCentreInteret(Long id);

}

