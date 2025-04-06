package com.centreinteret.centreinteret.controller;

import com.centreinteret.centreinteret.dto.AddresseDTO;
import com.centreinteret.centreinteret.dto.CategorieDTO;
import com.centreinteret.centreinteret.dto.CentreinteretDTO;
import com.centreinteret.centreinteret.dto.ReservationDTO;
import com.centreinteret.centreinteret.serviceImplement.CentreInteretServiceImplement;
import com.centreinteret.centreinteret.serviceImplement.ReservationServiceImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/centreinterets")
@CrossOrigin(origins = "http://localhost:8100",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE},
        allowedHeaders = "*")
public class CentreInteretController {

    @Autowired
    private CentreInteretServiceImplement centreInteretService;

    @Autowired
    private ReservationServiceImplement reservationService;

    // Endpoint pour récupérer tous les centres d'intérêt
    @GetMapping
    public ResponseEntity<List<CentreinteretDTO>> getAllCentreInterets() {
        List<CentreinteretDTO> centreInterets = centreInteretService.listeCentre();
        return new ResponseEntity<>(centreInterets, HttpStatus.OK);
    }

    // Endpoint pour récupérer un centre d'intérêt par ID
    @GetMapping("/{id}")
    public ResponseEntity<CentreinteretDTO> getCentreInteretById(@PathVariable Long id) {
        CentreinteretDTO centreInteret = centreInteretService.recupererParId(id);
        return new ResponseEntity<>(centreInteret, HttpStatus.OK);
    }

    // Endpoint pour ajouter un nouveau centre d'intérêt
    @PostMapping
    public ResponseEntity<CentreinteretDTO> ajouterCentreInteret(@RequestBody CentreinteretDTO centreinteretDTO) {
        CentreinteretDTO createdCentreInteret = centreInteretService.ajouterCentreInteret(centreinteretDTO);
        return new ResponseEntity<>(createdCentreInteret, HttpStatus.CREATED);
    }

    // Endpoint pour mettre à jour un centre d'intérêt
    @PutMapping("/{id}")
    public ResponseEntity<CentreinteretDTO> mettreAJourCentreInteret(
            @PathVariable Long id,
            @RequestBody CentreinteretDTO centreinteretDTO) {
        try {
            CentreinteretDTO updatedCentre = centreInteretService.mettreAJourCentreInteret(id, centreinteretDTO);
            return new ResponseEntity<>(updatedCentre, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    // Endpoint pour supprimer un centre d'intérêt
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCentreInteret(@PathVariable Long id) {
        centreInteretService.supprimerCentreInteret(id); // Ajoutez une méthode de suppression dans le service
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Endpoint pour récupérer la catégorie d'un centre d'intérêt par ID
    @GetMapping("/{id}/categories")
    public ResponseEntity<CategorieDTO> getCategorieById(@PathVariable Long id) {
        CategorieDTO categorie = centreInteretService.recupererCategorieParId(id);
        return new ResponseEntity<>(categorie, HttpStatus.OK);
    }

    // Endpoint pour créer une nouvelle réservation
    @PostMapping("/{id}/reservations")
    public ResponseEntity<ReservationDTO> createReservation(@PathVariable Long id, @RequestBody ReservationDTO reservationDTO) {
        reservationDTO.setCentreInteretId(id); // Associer l'ID du centre d'intérêt à la réservation
        ReservationDTO createdReservation = reservationService.createReservation(reservationDTO);
        return new ResponseEntity<>(createdReservation, HttpStatus.CREATED);
    }
    @GetMapping("/{centreId}/reservations/{reservationId}")
    public ResponseEntity<ReservationDTO> getReservationById(@PathVariable Long centreId, @PathVariable Long reservationId) {
        ReservationDTO reservation = reservationService.getReservationById(reservationId);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

    @DeleteMapping("/{centreId}/reservations/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long centreId, @PathVariable Long reservationId) {
        reservationService.deleteReservation(reservationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}