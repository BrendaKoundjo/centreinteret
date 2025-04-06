package com.centreinteret.centreinteret.serviceImplement;

import com.centreinteret.centreinteret.dto.ReservationDTO;
import com.centreinteret.centreinteret.entities.Reservation;
import com.centreinteret.centreinteret.entities.Centreinteret;
import com.centreinteret.centreinteret.repository.ReservationRepository;
import com.centreinteret.centreinteret.repository.CentreInteretRepository;
import com.centreinteret.centreinteret.services.ReservationService;
import com.centreinteret.centreinteret.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImplement implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private CentreInteretRepository centreInteretRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public ReservationDTO createReservation(ReservationDTO reservationDTO) {
        Reservation reservation = convertToEntity(reservationDTO);

        Reservation savedReservation = reservationRepository.save(reservation);

        Centreinteret centreInteret = savedReservation.getCentreInteret();
        String centreDetails = "Nom du centre : " + centreInteret.getNom() + "\n"
                + "Description : " + centreInteret.getDescription();

        // Envoyer un email de confirmation avec les détails du centre
        String emailText = "Votre réservation a été confirmée. Détails :\n"
                + "ID de réservation : " + savedReservation.getId() + "\n"
                + "Email : " + savedReservation.getEmail() + "\n"
                + "Date de réservation : " + savedReservation.getDateReservation() + "\n"
                + "Statut : " + (savedReservation.isStatut() ? "Confirmé" : "En attente") + "\n\n"
                + "Détails du centre d'intérêt :\n" + centreDetails;

        emailService.sendEmail(savedReservation.getEmail(), "Confirmation de Réservation", emailText);

        // Convertir Reservation en ReservationDTO
        return convertToDTO(savedReservation);
    }

    @Override
    public List<ReservationDTO> getAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ReservationDTO getReservationById(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
        return convertToDTO(reservation);
    }

    @Override
    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    private Reservation convertToEntity(ReservationDTO reservationDTO) {
        Reservation reservation = new Reservation();
        reservation.setId(reservationDTO.getId());
        reservation.setEmail(reservationDTO.getEmail());
        reservation.setDateReservation(reservationDTO.getDateReservation());
        reservation.setStatut(reservationDTO.isStatut());

        if (reservationDTO.getCentreInteretId() != null) {
            Centreinteret centreInteret = centreInteretRepository.findById(reservationDTO.getCentreInteretId())
                    .orElseThrow(() -> new RuntimeException("CentreInteret not found"));
            reservation.setCentreInteret(centreInteret);
        }

        return reservation;
    }

    private ReservationDTO convertToDTO(Reservation reservation) {
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setId(reservation.getId());
        reservationDTO.setEmail(reservation.getEmail());
        reservationDTO.setDateReservation(reservation.getDateReservation());
        reservationDTO.setStatut(reservation.isStatut());

        if (reservation.getCentreInteret() != null) {
            reservationDTO.setCentreInteretId(reservation.getCentreInteret().getId());
        }

        return reservationDTO;
    }
}