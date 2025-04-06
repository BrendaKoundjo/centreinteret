package com.centreinteret.centreinteret.controller;


import com.centreinteret.centreinteret.dto.ServiceIDTO;
import com.centreinteret.centreinteret.entities.ServiceI;
import com.centreinteret.centreinteret.repository.ServiceRepository;
import com.centreinteret.centreinteret.services.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/service")
@CrossOrigin(origins = "http://localhost:8100",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE},
        allowedHeaders = "*")
public class ServiceController {
    @Autowired
    private ServiceService serviceService;

    @GetMapping
    public ResponseEntity<List<ServiceIDTO>> listeService() {
        List<ServiceIDTO> services = serviceService.listeService();
        return new ResponseEntity<>(services, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<ServiceIDTO> createService(@RequestBody ServiceIDTO serviceIDTO) {
        ServiceIDTO createdService = serviceService.createService(serviceIDTO);
        return new ResponseEntity<>(createdService, HttpStatus.CREATED);
    }
    @GetMapping("/byCentre/{centreId}")
    public ResponseEntity<List<ServiceIDTO>> getServicesByCentre(@PathVariable Long centreId) {
        List<ServiceIDTO> services = serviceService.getServicesByCentre(centreId);
        if (services.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucun service trouvé pour ce centre.");
        }
        return new ResponseEntity<>(services, HttpStatus.OK);
    }
  }
