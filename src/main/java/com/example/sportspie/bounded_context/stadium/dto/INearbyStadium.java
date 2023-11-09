package com.example.sportspie.bounded_context.stadium.dto;

public interface INearbyStadium {
    Integer getId();
    String getName();
    Double getLatitude();
    Double getLongitude();
    Double getDistance();
}
