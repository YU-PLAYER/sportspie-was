package com.example.sportspie.bounded_context.stadium.service;

import com.example.sportspie.bounded_context.stadium.dto.INearbyStadium;
import com.example.sportspie.bounded_context.stadium.dto.NearbyStadiumRequestDto;
import com.example.sportspie.bounded_context.stadium.entity.Stadium;
import com.example.sportspie.bounded_context.stadium.repository.StadiumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StadiumService {
    private final StadiumRepository stadiumRepository;

    public List<Stadium> list() { return stadiumRepository.findAll();}

    public Stadium read(Integer id) {
        return stadiumRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 경기장이 없습니다. id= "+ id));
    }

    public List<INearbyStadium> list(String latitude, String longitude){
        return stadiumRepository.findNearbyStadium(Double.parseDouble(latitude), Double.parseDouble(longitude));
    }

}
