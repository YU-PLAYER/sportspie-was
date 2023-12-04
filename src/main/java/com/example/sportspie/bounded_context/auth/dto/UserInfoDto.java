package com.example.sportspie.bounded_context.auth.dto;

import com.example.sportspie.bounded_context.auth.entity.User;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserInfoDto {
	private String email;
	private String nickname;
	private String gender;
	private Integer age;
	private String region;
	private Integer height;
	private Integer weight;
	private String introduce;
	private Boolean attacker;
	private Boolean midfielder;
	private Boolean defender;
	private Boolean goalkeeper;
	private Boolean isPublicProfile;
	private Boolean isPublicInformation;
	private Boolean isPublicIntroduce;
	private Boolean isPublicRecord;
	private String imageUrl;

	public User toEntity(User user) {
		return User.builder()
				.username(user.getUsername())
				.email(email)
				.nickname(nickname)
				.gender(gender)
				.age(age)
				.region(region)
				.height(height)
				.weight(weight)
				.introduce(introduce)
				.attacker(attacker)
				.midfielder(midfielder)
				.defender(defender)
				.goalkeeper(goalkeeper)
				.publicProfile(isPublicProfile)
				.publicInformation(isPublicInformation)
				.publicIntroduce(isPublicIntroduce)
				.publicRecord(isPublicRecord)
				.imageUrl(imageUrl)
				.build();
	}

	@Builder
	public UserInfoDto(String email, String nickname, String gender, Integer age, String region, Integer height, Integer weight, String introduce,
			Boolean attacker,
			Boolean midfielder, Boolean defender, Boolean goalkeeper, Boolean isPublicProfile, Boolean isPublicInformation, Boolean isPublicIntroduce,
			Boolean isPublicRecord, String imageUrl) {
		this.email = email;
		this.nickname = nickname;
		this.gender = gender;
		this.age = age;
		this.region = region;
		this.height = height;
		this.weight = weight;
		this.introduce = introduce;
		this.attacker = attacker;
		this.midfielder = midfielder;
		this.defender = defender;
		this.goalkeeper = goalkeeper;
		this.isPublicProfile = isPublicProfile;
		this.isPublicInformation = isPublicInformation;
		this.isPublicIntroduce = isPublicIntroduce;
		this.isPublicRecord = isPublicRecord;
		this.imageUrl = imageUrl;
	}
}
