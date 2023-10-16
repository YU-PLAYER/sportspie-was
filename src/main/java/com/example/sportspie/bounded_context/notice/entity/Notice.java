package com.example.sportspie.bounded_context.notice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
public class Notice extends BaseTimeEntity {
    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Builder
    public Notice(String title, String content) {
    	this.title = title;
    	this.content = content;
    }
}
