package com.remetu.afyasoft.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfig implements CorsConfigurationSource {
    @Value("${spa.host.url}")
    private String spaHostUrl;

    @Override
    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin(spaHostUrl);
        config.setAllowedMethods(List.of("*"));
        config.setAllowedHeaders(List.of("*"));
        return config;
    }
}
