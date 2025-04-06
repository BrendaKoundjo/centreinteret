package com.centreinteret.centreinteret.dto;



import lombok.Data;

@Data
public class PlaceDetailsDTO {
    private String name;
    private String address;
    private String phoneNumber;
    private Float rating;
    private String placeId;
    private Boolean openNow;
    private String[] weekdayText;
    private String photoUrl;
    private double latitude;
    private double longitude;
}
