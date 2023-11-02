package com.example.sportspie.bounded_context.banner.controller;

import com.example.sportspie.base.api.BannerApi;
import com.example.sportspie.bounded_context.banner.entity.Banner;
import com.example.sportspie.bounded_context.banner.service.BannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BannerController implements BannerApi {
    private final BannerService bannerService;

    @Override
    public List<Banner> list() {
        return bannerService.list();
    }
}
