package com.example.sportspie.bounded_context.stadium.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "api-key")
@Getter
@Setter
public class StadiumConfig {
    private String serviceKey;
}
