package com.example.sportspie.bounded_context.auth.entity;

import java.util.Collection;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.sportspie.base.entity.BaseTimeEntity;
import com.example.sportspie.bounded_context.auth.dto.UserInfoDto;
import com.example.sportspie.bounded_context.auth.type.OAuthPlatform;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity implements UserDetails {
	@Column(nullable = false)
	private String username;

	@Column
	private String email;

	@Column
	private String nickname;

	@Column
	private String imageUrl;

	@Column(columnDefinition = "TINYINT", nullable = false)
	private OAuthPlatform platform;

	@Column
	private String gender;

	@Column
	private Integer age;

	@Column
	private String region;

	@Column
	private Integer height;

	@Column
	private Integer weight;

	@Column
	private String introduce;

	@Column(name = "is_preffered_attacker", columnDefinition = "TINYINT", nullable = false)
	@ColumnDefault("false")
	private Boolean attacker;

	@Column(name = "is_preffered_midfielder", columnDefinition = "TINYINT", nullable = false)
	@ColumnDefault("false")
	private Boolean midfielder;

	@Column(name = "is_preffered_defender", columnDefinition = "TINYINT", nullable = false)
	@ColumnDefault("false")
	private Boolean defender;

	@Column(name = "is_preffered_goalkeeper", columnDefinition = "TINYINT", nullable = false)
	@ColumnDefault("false")
	private Boolean goalkeeper;

	@Column(name = "is_public_profile", columnDefinition = "TINYINT", nullable = false)
	@ColumnDefault("false")
	private Boolean publicProfile;

	@Column(name = "is_public_information", columnDefinition = "TINYINT", nullable = false)
	@ColumnDefault("false")
	private Boolean publicInformation;

	@Column(name = "is_public_introduce", columnDefinition = "TINYINT", nullable = false)
	@ColumnDefault("false")
	private Boolean publicIntroduce;

	@Column(name = "is_public_record", columnDefinition = "TINYINT", nullable = false)
	@ColumnDefault("false")
	private Boolean publicRecord;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Builder
	public User(String username, String email, String nickname, String imageUrl, OAuthPlatform platform, String gender, Integer age, String region,
			Integer height, Integer weight, String introduce, Boolean attacker, Boolean midfielder, Boolean defender, Boolean goalkeeper,
			Boolean publicProfile, Boolean publicInformation, Boolean publicIntroduce, Boolean publicRecord) {
		this.username = username;
		this.email = email;
		this.nickname = nickname;
		this.imageUrl = imageUrl;
		this.platform = platform;
		this.gender = gender;
		this.age = age;
		this.region = region;
		this.height = height;
		this.weight = weight;
		this.introduce = introduce;

		this.attacker = attacker != null ? attacker : Boolean.FALSE;
		this.midfielder = midfielder != null ? midfielder : Boolean.FALSE;
		this.defender = defender != null ? defender : Boolean.FALSE;
		this.goalkeeper = goalkeeper != null ? goalkeeper : Boolean.FALSE;
		this.publicProfile = publicProfile != null ? publicProfile : Boolean.FALSE;
		this.publicInformation = publicInformation != null ? publicInformation : Boolean.FALSE;
		this.publicIntroduce = publicIntroduce != null ? publicIntroduce : Boolean.FALSE;
		this.publicRecord = publicRecord != null ? publicRecord : Boolean.FALSE;
	}

	public User update(UserInfoDto userInfoDto) {
		this.email = userInfoDto.getEmail();
		this.nickname = userInfoDto.getNickname();
		this.gender = userInfoDto.getGender();
		this.age = userInfoDto.getAge();
		this.region = userInfoDto.getRegion();
		this.height = userInfoDto.getHeight();
		this.weight = userInfoDto.getWeight();
		this.introduce = userInfoDto.getIntroduce();
		this.attacker = userInfoDto.getAttacker();
		this.midfielder = userInfoDto.getMidfielder();
		this.defender = userInfoDto.getDefender();
		this.goalkeeper = userInfoDto.getGoalkeeper();
		this.publicProfile = userInfoDto.getIsPublicProfile();
		this.publicInformation = userInfoDto.getIsPublicInformation();
		this.publicIntroduce = userInfoDto.getIsPublicIntroduce();
		this.publicRecord = userInfoDto.getIsPublicRecord();
		this.imageUrl = userInfoDto.getImageUrl();
		return this;
	}
}
