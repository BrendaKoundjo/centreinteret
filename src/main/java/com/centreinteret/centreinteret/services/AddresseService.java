package com.centreinteret.centreinteret.services;

import com.centreinteret.centreinteret.dto.AddresseDTO;
import com.centreinteret.centreinteret.entities.Addresse;

import java.util.List;

public interface AddresseService {
    AddresseDTO ajouterAdresse(AddresseDTO addresseDTO);
    AddresseDTO mettreAJourAdresse(Long id, AddresseDTO addresseDTO);
    AddresseDTO recupererParId(Long id);
   // List<AddresseDTO> recupererParCentreInteret(Long centreInteretId);
    void supprimerAdresse(Long id);
    AddresseDTO geocoderAdresse(AddresseDTO addresseDTO);
    List<AddresseDTO> rechercherParQuartier(String quartier);
    List<AddresseDTO> rechercherParVille(String ville);
    List<AddresseDTO> rechercherParPays(String pays);
    List<AddresseDTO> rechercherParNom(String pointderepere);


    List<AddresseDTO> listeCentre();
}
