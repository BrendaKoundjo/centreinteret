package com.centreinteret.centreinteret.serviceImplement;

import com.centreinteret.centreinteret.dto.CentreinteretDTO;
import org.springframework.stereotype.Service;

import com.centreinteret.centreinteret.dto.AddresseDTO;
import com.centreinteret.centreinteret.entities.Addresse;
import com.centreinteret.centreinteret.entities.Centreinteret;
import com.centreinteret.centreinteret.repository.AddresseRepository;
import com.centreinteret.centreinteret.repository.CentreInteretRepository;
import com.centreinteret.centreinteret.services.AddresseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImplement implements AddresseService {

    @Autowired
    private AddresseRepository addresseRepository;

    @Autowired
    private CentreInteretRepository centreInteretRepository;

    @Autowired
    private GoogleMapsService googleMapsService;

    @Override
    public AddresseDTO ajouterAdresse(AddresseDTO addresseDTO) {
        // Vérifiez si le Centre d'intérêt existe
        Centreinteret centreinteret = centreInteretRepository.findById(addresseDTO.getCentreInteretId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Centre d'intérêt non trouvé"));
        // Géocoder l'adresse pour obtenir lat/lng
        AddresseDTO geocodedAddress = geocoderAdresse(addresseDTO);

        Addresse addresse = new Addresse();
        updateAddresseFromDTO(addresse, geocodedAddress);
       addresse.setCentreinteret(centreinteret);

        Addresse savedAddress = addresseRepository.save(addresse);
        return mapToDTO(savedAddress);
    }

    @Override
    public AddresseDTO mettreAJourAdresse(Long id, AddresseDTO addresseDTO) {
        Addresse existingAddress = addresseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Adresse non trouvée"));

        // Géocoder la nouvelle adresse
        AddresseDTO geocodedAddress = geocoderAdresse(addresseDTO);

        updateAddresseFromDTO(existingAddress, geocodedAddress);
        Addresse updatedAddress = addresseRepository.save(existingAddress);
        return mapToDTO(updatedAddress);
    }

    @Override
    public AddresseDTO recupererParId(Long id) {
        return addresseRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Adresse non trouvée"));
    }

   // @Override
//    public List<AddresseDTO> recupererParCentreInteret(Long centreInteretId) {
//        return addresseRepository.findByCentreinteretId(centreInteretId)
//                .stream()
//                .map(this::mapToDTO)
//                .collect(Collectors.toList());
//    }


    @Override
    public void supprimerAdresse(Long id) {
        if (!addresseRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Adresse non trouvée");
        }
        addresseRepository.deleteById(id);
    }


    @Override
    public AddresseDTO geocoderAdresse(AddresseDTO addresseDTO) {
        // Construire l'adresse complète pour le géocodage
        String fullAddress = String.format("%s, %s, %s, %s",
                addresseDTO.getPointDeRepare(),
                addresseDTO.getQuartier(),
                addresseDTO.getVille(),
                addresseDTO.getPays());

        // Récupérer les données géocodées
        AddresseDTO geocodedAddress = googleMapsService.geocodeAddress(fullAddress);

        // Préserver les données originales importantes
        if (geocodedAddress != null) {
            geocodedAddress.setPointDeRepare(addresseDTO.getPointDeRepare());
            // Si certaines valeurs sont nulles dans le résultat du géocodage,
            // utiliser les valeurs originales
            if (geocodedAddress.getQuartier() == null) {
                geocodedAddress.setQuartier(addresseDTO.getQuartier());
            }
            if (geocodedAddress.getVille() == null) {
                geocodedAddress.setVille(addresseDTO.getVille());
            }
            if (geocodedAddress.getPays() == null) {
                geocodedAddress.setPays(addresseDTO.getPays());
            }
        }

        return geocodedAddress;
    }

    @Override
    public List<AddresseDTO> rechercherParQuartier(String quartier) {
        return addresseRepository.findByQuartierContainingIgnoreCase(quartier)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AddresseDTO> rechercherParNom(String pointderepere) {
        return addresseRepository.findByPointDeRepareContainingIgnoreCase(pointderepere)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AddresseDTO> rechercherParPays(String pays) {
        return addresseRepository.findByPaysContainingIgnoreCase(pays)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AddresseDTO> rechercherParVille(String ville) {
        return addresseRepository.findByVilleContainingIgnoreCase(ville)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }



    private void updateAddresseFromDTO(Addresse addresse, AddresseDTO dto) {
        addresse.setPointDeRepare(dto.getPointDeRepare());
        addresse.setQuartier(dto.getQuartier());
        addresse.setVille(dto.getVille());
        addresse.setPays(dto.getPays());
        addresse.setLatitude(dto.getLatitude());
        addresse.setLongitude(dto.getLongitude());
        addresse.setAdresseFormatee(dto.getAdresseFormatee());
        addresse.setPlaceId(dto.getPlaceId());
    }

    private AddresseDTO mapToDTO(Addresse addresse) {
        AddresseDTO dto = new AddresseDTO();
        dto.setId(addresse.getId());
        dto.setPointDeRepare(addresse.getPointDeRepare());
        dto.setQuartier(addresse.getQuartier());
        dto.setVille(addresse.getVille());
        dto.setPays(addresse.getPays());
        dto.setLatitude(addresse.getLatitude());
        dto.setLongitude(addresse.getLongitude());
        dto.setAdresseFormatee(addresse.getAdresseFormatee());
        dto.setPlaceId(addresse.getPlaceId());
        if (addresse.getCentreinteret() != null) {
            dto.setCentreInteretId(addresse.getCentreinteret().getId());
        } else {
            dto.setCentreInteretId(null); // Ou gérez cela comme vous le souhaitez
        }
        /*dto.setCentreInteretId(addresse.getCentreinteret().getId());*/
        return dto;
    }

    @Override
    public List<AddresseDTO> listeCentre() {
        return addresseRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
}
