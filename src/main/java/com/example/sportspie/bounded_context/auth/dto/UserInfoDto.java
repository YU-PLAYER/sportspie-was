package com.example.sportspie.bounded_context.auth.dto;

import com.example.sportspie.bounded_context.auth.entity.User;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

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
				.build();
	}
}
