package com.centreinteret.centreinteret.serviceImplement;

import com.google.maps.*;
import com.google.maps.model.*;
import com.centreinteret.centreinteret.dto.AddresseDTO;
import com.centreinteret.centreinteret.dto.PlaceDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class GoogleMapsService {

    @Autowired
    private GeoApiContext geoApiContext;

    @Value("${google.maps.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public GoogleMapsService() {
        this.restTemplate = new RestTemplate();
    }

    // Géocodage d'adresse
    public AddresseDTO geocodeAddress(String address) {
        try {
            GeocodingResult[] results = GeocodingApi.geocode(geoApiContext, address).await();
            if (results != null && results.length > 0) {
                AddresseDTO addresseDTO = new AddresseDTO();

                // Ajout des coordonnées et informations de base
                addresseDTO.setLatitude(results[0].geometry.location.lat);
                addresseDTO.setLongitude(results[0].geometry.location.lng);
                addresseDTO.setAdresseFormatee(results[0].formattedAddress);
                addresseDTO.setPlaceId(results[0].placeId);

                // Extraction des composants d'adresse
                for (AddressComponent component : results[0].addressComponents) {
                    for (AddressComponentType type : component.types) {
                        switch (type) {
                            case LOCALITY:
                                addresseDTO.setVille(component.longName);
                                break;
                            case SUBLOCALITY_LEVEL_1: // Changement ici pour mieux gérer le quartier
                                addresseDTO.setQuartier(component.longName);
                                break;
                            case COUNTRY:
                                addresseDTO.setPays(component.longName);
                                break;
                        }
                    }
                }

                // Si le quartier n'a pas été trouvé avec SUBLOCALITY_LEVEL_1, essayez ADMINISTRATIVE_AREA_LEVEL_1
                if (addresseDTO.getQuartier() == null) {
                    for (AddressComponent component : results[0].addressComponents) {
                        for (AddressComponentType type : component.types) {
                            if (type == AddressComponentType.ADMINISTRATIVE_AREA_LEVEL_1) {
                                addresseDTO.setQuartier(component.longName);
                                break;
                            }
                        }
                    }
                }

                return addresseDTO;
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du géocodage de l'adresse", e);
        }
    }

    // Calcul de distance entre deux points
    public DirectionsResult getDirections(LatLng origin, LatLng destination, TravelMode mode) {
        try {
            return DirectionsApi.newRequest(geoApiContext)
                    .origin(origin)
                    .destination(destination)
                    .mode(mode)
                    .await();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du calcul de l'itinéraire", e);
        }
    }

    // Recherche de lieux à proximité
    public PlacesSearchResult[] searchNearbyPlaces(LatLng location, int radius, String type) {
        try {
            return PlacesApi.nearbySearchQuery(geoApiContext, location)
                    .radius(radius)
                    .type(PlaceType.valueOf(type.toUpperCase()))
                    .await()
                    .results;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche de lieux à proximité", e);
        }
    }

    // Obtenir les détails d'un lieu
    public PlaceDetailsDTO getPlaceDetails(String placeId) {
        try {
            PlaceDetails details = PlacesApi.placeDetails(geoApiContext, placeId)
                    .fields(
                            PlaceDetailsRequest.FieldMask.FORMATTED_ADDRESS,
                            PlaceDetailsRequest.FieldMask.GEOMETRY,
                            PlaceDetailsRequest.FieldMask.ICON,
                            PlaceDetailsRequest.FieldMask.NAME,
                            PlaceDetailsRequest.FieldMask.PHOTOS,
                            PlaceDetailsRequest.FieldMask.PLACE_ID,
                            PlaceDetailsRequest.FieldMask.RATING,
                            PlaceDetailsRequest.FieldMask.FORMATTED_PHONE_NUMBER,
                            PlaceDetailsRequest.FieldMask.OPENING_HOURS
                    )
                    .await();

            PlaceDetailsDTO dto = new PlaceDetailsDTO();
            dto.setName(details.name);
            dto.setAddress(details.formattedAddress);
            dto.setPhoneNumber(details.formattedPhoneNumber);
            dto.setRating(details.rating);
            dto.setPlaceId(details.placeId);

            if (details.openingHours != null) {
                dto.setOpenNow(details.openingHours.openNow);
                dto.setWeekdayText(details.openingHours.weekdayText);
            }

            return dto;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des détails du lieu", e);
        }
    }

    // Obtenir l'URL de Street View
    public String getStreetViewUrl(double lat, double lng, int width, int height) {
        return String.format(
                "https://maps.googleapis.com/maps/api/streetview?size=%dx%d&location=%f,%f&key=%s",
                width, height, lat, lng, apiKey
        );
    }

    // Obtenir l'URL de Static Map
    public String getStaticMapUrl(double lat, double lng, int zoom, int width, int height) {
        return String.format(
                "https://maps.googleapis.com/maps/api/staticmap?center=%f,%f&zoom=%d&size=%dx%d&key=%s",
                lat, lng, zoom, width, height, apiKey
        );
    }

    // Calculer la distance entre deux points
    public double calculateDistance(LatLng origin, LatLng destination) {
        try {
            DistanceMatrix matrix = DistanceMatrixApi.newRequest(geoApiContext)
                    .origins(origin)
                    .destinations(destination)
                    .await();

            if (matrix.rows.length > 0 && matrix.rows[0].elements.length > 0) {
                return matrix.rows[0].elements[0].distance.inMeters / 1000.0; // Convert to kilometers
            }
            return -1;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du calcul de la distance", e);
        }
    }
}