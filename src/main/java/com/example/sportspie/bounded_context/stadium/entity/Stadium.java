package com.example.sportspie.bounded_context.stadium.entity;

import com.example.sportspie.base.entity.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Stadium extends BaseTimeEntity {
	@Column(nullable = false)
	private String city;

	@Column(nullable = false)
	private String district;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private Double latitude;

	@Column(nullable = false)
	private Double longitude;

	@Column
	private String imageUrl;
}
