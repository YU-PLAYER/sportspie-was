package com.example.sportspie.banner.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Banner {

    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String image_url;

    @Column(nullable = false)
    private String link;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;
}
