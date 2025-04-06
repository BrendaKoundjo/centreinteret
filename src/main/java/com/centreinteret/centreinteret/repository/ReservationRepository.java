package com.centreinteret.centreinteret.repository;

import com.centreinteret.centreinteret.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository  extends JpaRepository<Reservation, Long> {
}
