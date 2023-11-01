package com.example.sportspie.base.api;

import com.example.sportspie.bounded_context.banner.entity.Banner;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/banner")
@Tag(name = "Banner", description = "Banner 관련 API")
public interface BannerApi {
    @GetMapping("")
    @Operation(summary = "Banner 관련 메서드", description = "사용자가 Banner를 사용하기 위한 메서드입니다.")
    List<Banner> list();
}
