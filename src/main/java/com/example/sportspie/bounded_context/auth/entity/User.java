package com.example.sportspie.bounded_context.auth.entity;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.sportspie.base.entity.BaseTimeEntity;
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

	@Column(nullable = false)
	private String password;

	@Column
	private String email;

	@Column
	private String nickname;

	@Column
	private String imageUrl;

	@Column(columnDefinition = "TINYINT", nullable = false)
	private Integer isReadable;

	@Column(columnDefinition = "TINYINT", nullable = false)
	private OAuthPlatform oAuthPlatform;

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
	public User(String username, String password, String email, String nickname, String imageUrl, Integer isReadable) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.nickname = nickname;
		this.imageUrl = imageUrl;
		this.isReadable = isReadable;
	}
}
