package com.centreinteret.centreinteret.services;

import com.centreinteret.centreinteret.dto.ServiceIDTO;

import java.util.List;

public interface ServiceService {
    List<ServiceIDTO> listeService();
    ServiceIDTO createService(ServiceIDTO serviceIDTO);
    List<ServiceIDTO> getServicesByCentre(Long centreId);

}
