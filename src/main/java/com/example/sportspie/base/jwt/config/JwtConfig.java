package com.example.sportspie.base.jwt.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "jwt")
@Getter
@Setter
public class JwtConfig {
	private String secretKey;
	private long expirationTime;
	private String tokenPrefix;
	private String HeaderString;
	private String issuer;
}