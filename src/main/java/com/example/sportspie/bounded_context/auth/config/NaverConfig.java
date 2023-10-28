package com.example.sportspie.bounded_context.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "oauth2.naver")
@Getter
@Setter
public class NaverConfig {
	private String userInfoUri;
}
