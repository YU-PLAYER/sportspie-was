package com.example.sportspie.bounded_context.banner.repository;

import com.example.sportspie.bounded_context.banner.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannerRepository extends JpaRepository<Banner, Long> {
}
