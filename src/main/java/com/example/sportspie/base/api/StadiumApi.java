package com.example.sportspie.base.api;

import com.example.sportspie.bounded_context.stadium.dto.INearbyStadium;
import com.example.sportspie.bounded_context.stadium.dto.NearbyStadiumRequestDto;
import com.example.sportspie.bounded_context.stadium.entity.Stadium;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/api/stadium")
@Tag(name = "Stadium", description = "경기장 API")
public interface StadiumApi {
    @GetMapping("")
    @Operation(summary = "경기장 목록 조회 메서드", description = "사용자가 경기장 목록을 조회하기 위한 메서드입니다.")
    List<Stadium> list();

    @GetMapping("/{id}")
    @Operation(summary = "경기장 상세 조회 메서드", description = "사용자가 경기장의 상세 정보를 조회하기 위한 메서드입니다.")
    Stadium read(@PathVariable Integer id);

    @GetMapping("/nearby")
    @Operation(summary = "사용자 주변 경기장 목록 조회 메서드", description = "사용자 주변의 경기장 목록을 조회하기 위한 메서드입니다.")
    List<INearbyStadium> list(@RequestParam(value="latitude", required = false, defaultValue = "35.83602273380733") String latitude,
                              @RequestParam(value="longitude", required = false, defaultValue = "128.75268080348934") String longitude);
}
