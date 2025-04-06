package com.centreinteret.centreinteret.services;


import com.centreinteret.centreinteret.dto.ReservationDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ReservationService {
  ReservationDTO createReservation(ReservationDTO reservationDTO);
        List<ReservationDTO> getAllReservations();
        ReservationDTO getReservationById(Long id);
        void deleteReservation(Long id);
}