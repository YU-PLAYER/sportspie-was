package com.example.sportspie.bounded_context.banner.service;

import com.example.sportspie.bounded_context.banner.entity.Banner;
import com.example.sportspie.bounded_context.banner.repository.BannerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BannerService {
    private final BannerRepository bannerRepository;

    public List<Banner> list() {
        return bannerRepository.findAll();
    }
}
