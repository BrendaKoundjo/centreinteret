package com.centreinteret.centreinteret.serviceImplement;

import com.centreinteret.centreinteret.dto.*;
import com.centreinteret.centreinteret.entities.*;
import com.centreinteret.centreinteret.repository.CategorieRepository;
import com.centreinteret.centreinteret.repository.CentreInteretRepository;
import com.centreinteret.centreinteret.repository.ReservationRepository;
import com.centreinteret.centreinteret.services.centreInteretService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CentreInteretServiceImplement implements centreInteretService {

    @Autowired
    private CentreInteretRepository centreInteretRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private CategorieRepository categorieRepository;



    @Override
    public List<CentreinteretDTO> listeCentre() {
        return centreInteretRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    @Override
    public CentreinteretDTO recupererParId(Long id) {
        return centreInteretRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Centre d'intérêt non trouvé"));
    }

//    @Override
//    public List<CentreinteretDTO> rechercherParProximite(Double latitude, Double longitude, Double rayon) {
//        // Utilise la formule de Haversine pour calculer la distance
//        return centreInteretRepository.findAll().stream()
//                .filter(centre -> calculateDistance(latitude, longitude,
//                        centre.getLatitude(), centre.getLongitude()) <= rayon)
//                .map(this::mapToDTO)
//                .collect(Collectors.toList());
//    }

//    @Override
//    public CentreinteretDTO ajouterCentreInteret(CentreinteretDTO centreinteretDTO) {
//        // Vérification de la catégorie
//        if (!categorieRepository.existsById(centreinteretDTO.getCategorieId())) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Catégorie non valide");
//        }
//
//        // Validation des coordonnées
//        validateCoordinates(centreinteretDTO.getLatitude(), centreinteretDTO.getLongitude());
//
//        Centreinteret centreinteret = new Centreinteret();
//        updateCentreInteretFromDTO(centreinteret, centreinteretDTO);
//
//        Centreinteret savedCentre = centreInteretRepository.save(centreinteret);
//        return mapToDTO(savedCentre);
//    }

    @Override
    public CentreinteretDTO mettreAJourCentreInteret(Long id, CentreinteretDTO centreinteretDTO) {
        Centreinteret existingCentre = centreInteretRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Centre d'intérêt non trouvé"));

        updateCentreInteretFromDTO(existingCentre, centreinteretDTO);

        Centreinteret updatedCentre = centreInteretRepository.save(existingCentre);
        return mapToDTO(updatedCentre);
    }

    private void updateCentreInteretFromDTO(Centreinteret centreinteret, CentreinteretDTO dto) {
        centreinteret.setNom(dto.getNom());
        centreinteret.setDescription(dto.getDescription());
        centreinteret.setActif(dto.isActif());
        centreinteret.setDisponible(dto.isDisponible());
        centreinteret.setImage(dto.getImage());
//        centreinteret.setLatitude(dto.getLatitude());
//        centreinteret.setLongitude(dto.getLongitude());
//        centreinteret.setAdresseFormatee(dto.getAdresseFormatee());
//        centreinteret.setPlaceId(dto.getPlaceId());
//        centreinteret.setUrlPhoto(dto.getUrlPhoto());
//        centreinteret.setHoraires(dto.getHoraires());
//        centreinteret.setNote(dto.getNote());
//        centreinteret.setNombreAvis(dto.getNombreAvis());

        // Récupération et association de la catégorie
        Categorie categorie = categorieRepository.findById(dto.getCategorieId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Catégorie non trouvée"));
        centreinteret.setCategorie(categorie);
        //Récuération et association de l'addresse

    }

    private CentreinteretDTO mapToDTO(Centreinteret centreinteret) {
        List<AddresseDTO> adressesDTO = (centreinteret.getAdresses() != null ?
                centreinteret.getAdresses().stream()
                        .map(addresse -> {
                            // Vérifiez si l'adresse est null
                            if (addresse == null) {
                                return null; // Ou créez une adresse par défaut si nécessaire
                            }
                            return new AddresseDTO(
                                    addresse.getId(),
                                    addresse.getPointDeRepare(), // Assurez-vous que ces attributs sont bien définis dans AddresseDTO
                                    addresse.getQuartier(),
                                    addresse.getVille(),
                                    addresse.getPays(),
                                    addresse.getLatitude(),
                                    addresse.getLongitude(),
                                    addresse.getAdresseFormatee(),
                                    addresse.getPlaceId(),
                                    addresse.getCentreinteret() != null ? addresse.getCentreinteret().getId() : null // Vérifiez si centreinteret est null
                            );
                        })
                        .filter(Objects::nonNull) // Filtrer les adresses null
                        .collect(Collectors.toList()) : new ArrayList<>()); // Initialiser à une liste vide si null

        return new CentreinteretDTO(
                centreinteret.getId(),
                centreinteret.getNom(),
                centreinteret.getDescription(),
                centreinteret.getCategorie().getId(),
                centreinteret.isActif(),
                centreinteret.isDisponible(),
                centreinteret.getImage(),
                adressesDTO
        );
    }

//    private void validateCoordinates(Double latitude, Double longitude) {
//        if (latitude == null || longitude == null) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Les coordonnées sont requises");
//        }
//        if (latitude < -90 || latitude > 90) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Latitude invalide");
//        }
//        if (longitude < -180 || longitude > 180) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Longitude invalide");
//        }
//    }
//
//    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
//        final int R = 6371; // Rayon de la Terre en kilomètres
//
//        double latDistance = Math.toRadians(lat2 - lat1);
//        double lonDistance = Math.toRadians(lon2 - lon1);
//        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
//                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
//                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
//        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//
//        return R * c;
//    }


    @Override
    public CategorieDTO recupererCategorieParId(Long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid category ID: " + id);
        }

        return categorieRepository.findById(id)
                .map(categorie -> new CategorieDTO(categorie.getId(), categorie.getNom()))
                .orElseThrow(() -> new EntityNotFoundException("Catégorie non trouvée avec l'ID: " + id));
    }


    @Override
    public void supprimerCentreInteret(Long id) {
        Centreinteret centreinteret = centreInteretRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Centre d'intérêt non trouvé"));
        centreInteretRepository.delete(centreinteret);
    }

    // Nouvelle méthode pour ajouter un centre d'intérêt
  @Override
    public CentreinteretDTO ajouterCentreInteret(CentreinteretDTO centreinteretDTO) {
        if (categorieRepository.existsById(centreinteretDTO.getCategorieId())) {
            Centreinteret centreinteret = new Centreinteret();
            centreinteret.setNom(centreinteretDTO.getNom());
            centreinteret.setDescription(centreinteretDTO.getDescription());
            centreinteret.setActif(false); // Par défaut
            centreinteret.setDisponible(false); // Par défaut
            centreinteret.setImage(centreinteretDTO.getImage());

            Categorie categorie = categorieRepository.findById(centreinteretDTO.getCategorieId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Catégorie non trouvée"));
            centreinteret.setCategorie(categorie);

            Centreinteret savedCentre = centreInteretRepository.save(centreinteret);
            return mapToDTO(savedCentre);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Catégorie non valide");
        }
    }





}