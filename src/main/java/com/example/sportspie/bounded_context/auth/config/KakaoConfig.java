package com.example.sportspie.bounded_context.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "oauth2.kakao")
@Getter
@Setter
public class KakaoConfig {
	private String userInfoUri;
	private String tokenUri;
	private String clientId;
	private String clientSecret;
	private String redirectUri;
}
