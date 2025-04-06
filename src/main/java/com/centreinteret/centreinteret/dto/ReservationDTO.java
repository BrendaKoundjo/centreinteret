package com.centreinteret.centreinteret.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {
    private Long id;
    private String email;
    private LocalDateTime dateReservation;
    private boolean statut;
    private Long centreInteretId;

}
