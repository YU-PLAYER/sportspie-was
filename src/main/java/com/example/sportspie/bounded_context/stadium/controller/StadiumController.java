package com.example.sportspie.bounded_context.stadium.controller;


import com.example.sportspie.base.api.StadiumApi;
import com.example.sportspie.bounded_context.stadium.dto.INearbyStadium;
import com.example.sportspie.bounded_context.stadium.dto.NearbyStadiumRequestDto;
import com.example.sportspie.bounded_context.stadium.entity.Stadium;
import com.example.sportspie.bounded_context.stadium.service.StadiumService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StadiumController implements StadiumApi {
    private final StadiumService stadiumService;

    @Override
    public List<Stadium> list() { return stadiumService.list();}

    @Override
    public Stadium read(Integer id) { return stadiumService.read(id);}

    @Override
    public List<INearbyStadium> list(String latitude, String longitude) {
        return stadiumService.list(latitude, longitude);
    }


}
