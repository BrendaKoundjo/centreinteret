package com.centreinteret.centreinteret.serviceImplement;

import com.centreinteret.centreinteret.dto.ServiceIDTO;
import com.centreinteret.centreinteret.entities.Centreinteret;
import com.centreinteret.centreinteret.entities.ServiceI;
import com.centreinteret.centreinteret.repository.CentreInteretRepository;
import com.centreinteret.centreinteret.repository.ServiceRepository;
import com.centreinteret.centreinteret.services.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceImplement implements ServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private CentreInteretRepository centreInteretRepository;

    @Override
    public ServiceIDTO createService(ServiceIDTO serviceIDTO) {
        ServiceI service = convertToEntity(serviceIDTO);
        ServiceI savedService = serviceRepository.save(service);
        return convertToDTO(savedService);
    }

    @Override
    public List<ServiceIDTO> listeService() {
        List<ServiceI> services = serviceRepository.findAll();
        return services.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ServiceIDTO> getServicesByCentre(Long centreId) {
        List<ServiceI> services = serviceRepository.findByCentreInteretId(centreId); // Utilisez le nom correct
        return services.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ServiceI convertToEntity(ServiceIDTO serviceIDTO) {
        ServiceI service = new ServiceI();
        service.setIntitule(serviceIDTO.getIntitule());
        service.setDescription(serviceIDTO.getDescription());
        service.setPrix(serviceIDTO.getPrix());
        service.setImage(serviceIDTO.getImage());
        service.setNom(serviceIDTO.getNom());

        // Récupérer l'objet Centreinteret à partir de l'ID
        Centreinteret centreInteret = centreInteretRepository.findById(serviceIDTO.getCentreInteretId())
                .orElseThrow(() -> new RuntimeException("Centre d'intérêt non trouvé avec l'ID : " + serviceIDTO.getCentreInteretId()));
        service.setCentreInteret(centreInteret);

        return service;
    }

    private ServiceIDTO convertToDTO(ServiceI service) {
        ServiceIDTO serviceIDTO = new ServiceIDTO();
        serviceIDTO.setId(service.getId());
        serviceIDTO.setIntitule(service.getIntitule());
        serviceIDTO.setDescription(service.getDescription());
        serviceIDTO.setPrix(service.getPrix());
        serviceIDTO.setImage(service.getImage());
        serviceIDTO.setNom(service.getNom());
        serviceIDTO.setCentreInteretId(service.getCentreInteret().getId()); // Récupérer l'ID du centre d'intérêt
        return serviceIDTO;
    }
}