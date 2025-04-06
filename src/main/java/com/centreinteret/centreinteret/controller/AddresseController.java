package com.centreinteret.centreinteret.controller;

import com.centreinteret.centreinteret.dto.AddresseDTO;
import com.centreinteret.centreinteret.dto.CentreinteretDTO;
import com.centreinteret.centreinteret.serviceImplement.AddressServiceImplement;
import com.centreinteret.centreinteret.serviceImplement.GoogleMapsService;
import com.google.maps.model.LatLng;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@CrossOrigin(origins = "http://localhost:4200",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE},
        allowedHeaders = "*")
public class AddresseController {

    @Autowired
    private GoogleMapsService googleMapsService;

    @Autowired
    private AddressServiceImplement addresseService;

    @PostMapping("/geocode")
    public ResponseEntity<AddresseDTO> geocodeAddress(@RequestBody String address) {
        AddresseDTO result = googleMapsService.geocodeAddress(address);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/distance")
    public ResponseEntity<Double> calculateDistance(
            @RequestParam Double originLat,
            @RequestParam Double originLng,
            @RequestParam Double destLat,
            @RequestParam Double destLng) {
        LatLng origin = new LatLng(originLat, originLng);
        LatLng destination = new LatLng(destLat, destLng);
        double distance = googleMapsService.calculateDistance(origin, destination);
        return ResponseEntity.ok(distance);
    }

    @PostMapping
    public ResponseEntity<AddresseDTO> ajouterAdresse(@RequestBody AddresseDTO addresseDTO) {
        AddresseDTO nouvelleAdresse = addresseService.ajouterAdresse(addresseDTO);
        return new ResponseEntity<>(nouvelleAdresse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddresseDTO> mettreAJourAdresse(@PathVariable Long id, @RequestBody AddresseDTO addresseDTO) {
        AddresseDTO adresseModifiee = addresseService.mettreAJourAdresse(id, addresseDTO);
        return new ResponseEntity<>(adresseModifiee, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddresseDTO> recupererAdresse(@PathVariable Long id) {
        AddresseDTO adresse = addresseService.recupererParId(id);
        return new ResponseEntity<>(adresse, HttpStatus.OK);
    }

//    @GetMapping("/centre/{centreInteretId}")
//    public ResponseEntity<List<AddresseDTO>> recupererAdressesParCentreInteret(@PathVariable Long centreInteretId) {
//        List<AddresseDTO> adresses = addresseService.recupererParCentreInteret(centreInteretId);
//        return new ResponseEntity<>(adresses, HttpStatus.OK);
//    }

    @GetMapping("/liste")
    public ResponseEntity<List<AddresseDTO>> getAllAddress() {
        List<AddresseDTO> address = addresseService.listeCentre();
        return new ResponseEntity<>(address, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerAdresse(@PathVariable Long id) {
        addresseService.supprimerAdresse(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/quartier/{quartier}")
    public ResponseEntity<List<AddresseDTO>> rechercherParQuartier(@PathVariable String quartier) {
        List<AddresseDTO> adresses = addresseService.rechercherParQuartier(quartier);
        return new ResponseEntity<>(adresses, HttpStatus.OK);
    }

    @GetMapping("/ville/{ville}")
    public ResponseEntity<List<AddresseDTO>> rechercherParVille(@PathVariable String ville) {
        List<AddresseDTO> adresses = addresseService.rechercherParVille(ville);
        return new ResponseEntity<>(adresses, HttpStatus.OK);
    }

    @GetMapping("/pointderepere/{pointDeRepere}")
    public ResponseEntity<List<AddresseDTO>> rechercherParNom(@PathVariable String pointDeRepere) {
        List<AddresseDTO> adresses = addresseService.rechercherParNom(pointDeRepere);
        return new ResponseEntity<>(adresses, HttpStatus.OK);
    }

    @GetMapping("/pays/{pays}")
    public ResponseEntity<List<AddresseDTO>> rechercherParPays(@PathVariable String pays) {
        List<AddresseDTO> adresses = addresseService.rechercherParPays(pays);
        return new ResponseEntity<>(adresses, HttpStatus.OK);
    }
}
