package com.example.sportspie.bounded_context.stadium.dto;

public interface INearbyStadium {
    Integer getId();
    String getName();
    String getCity();
    String getDistrict();
    String getVillage();
    Double getLatitude();
    Double getLongitude();
    Double getDistance();
}
