package com.centreinteret.centreinteret.controller;

import com.centreinteret.centreinteret.dto.AddresseDTO;
import com.centreinteret.centreinteret.dto.PlaceDetailsDTO;
import com.centreinteret.centreinteret.serviceImplement.GoogleMapsService;
import com.google.maps.model.LatLng;
import com.google.maps.model.PlacesSearchResult;
import com.google.maps.model.TravelMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/maps")
@CrossOrigin(origins = "http://localhost:4200",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE},
        allowedHeaders = "*")
public class GoogleMapsController {

    @Autowired
    private GoogleMapsService googleMapsService;

    @GetMapping("/geocode")
    public ResponseEntity<AddresseDTO> geocodeAddress(@RequestParam String address) {
        AddresseDTO result = googleMapsService.geocodeAddress(address);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/directions")
    public ResponseEntity<?> getDirections(
            @RequestParam Double originLat,
            @RequestParam Double originLng,
            @RequestParam Double destLat,
            @RequestParam Double destLng,
            @RequestParam(defaultValue = "DRIVING") String mode) {
        LatLng origin = new LatLng(originLat, originLng);
        LatLng destination = new LatLng(destLat, destLng);
        return ResponseEntity.ok(googleMapsService.getDirections(origin, destination, TravelMode.valueOf(mode)));
    }

    @GetMapping("/places/nearby")
    public ResponseEntity<PlacesSearchResult[]> searchNearbyPlaces(
            @RequestParam Double lat,
            @RequestParam Double lng,
            @RequestParam Integer radius,
            @RequestParam String type) {
        return ResponseEntity.ok(googleMapsService.searchNearbyPlaces(new LatLng(lat, lng), radius, type));
    }

    @GetMapping("/places/{placeId}")
    public ResponseEntity<PlaceDetailsDTO> getPlaceDetails(@PathVariable String placeId) {
        return ResponseEntity.ok(googleMapsService.getPlaceDetails(placeId));
    }

    @GetMapping("/streetview")
    public ResponseEntity<String> getStreetViewUrl(
            @RequestParam Double lat,
            @RequestParam Double lng,
            @RequestParam(defaultValue = "600") Integer width,
            @RequestParam(defaultValue = "400") Integer height) {
        return ResponseEntity.ok(googleMapsService.getStreetViewUrl(lat, lng, width, height));
    }

    @GetMapping("/staticmap")
    public ResponseEntity<String> getStaticMapUrl(
            @RequestParam Double lat,
            @RequestParam Double lng,
            @RequestParam(defaultValue = "15") Integer zoom,
            @RequestParam(defaultValue = "600") Integer width,
            @RequestParam(defaultValue = "400") Integer height) {
        return ResponseEntity.ok(googleMapsService.getStaticMapUrl(lat, lng, zoom, width, height));
    }
}