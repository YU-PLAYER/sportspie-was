package com.example.sportspie.bounded_context.banner.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import com.example.sportspie.base.entity.BaseTimeEntity;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Banner extends BaseTimeEntity {
    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private String link;

    @Builder
    public Banner(String imageUrl, String link) {
    	this.imageUrl = imageUrl;
    	this.link = link;
    }
}
