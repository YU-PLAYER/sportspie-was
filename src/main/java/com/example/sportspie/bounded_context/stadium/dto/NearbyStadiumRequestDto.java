package com.example.sportspie.bounded_context.stadium.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NearbyStadiumRequestDto implements INearbyStadium {
    private Integer id;
    private String name;
    private Double latitude;
    private Double longitude;
    private Double distance;
}
