package com.radz.wfh.config;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Value("${allowed.origin}")
  private String allowedOrigin;

  @Bean
  protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http.cors(
            httpSecurityCorsConfigurer ->
                httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()))
        .csrf(c -> c.ignoringRequestMatchers("/h2-console/**", "/wfh/getWfhRefQuantity"))
        .headers(
            httpSecurityHeadersConfigurer ->
                httpSecurityHeadersConfigurer.frameOptions(
                    HeadersConfigurer.FrameOptionsConfig::sameOrigin))
        .authorizeHttpRequests(
            request -> request.requestMatchers("/actuator/health/**", "/wfh/getWfhRefQuantity").permitAll().requestMatchers("/wfh/**").authenticated().anyRequest().permitAll())
        .oauth2ResourceServer(
            httpSecurityOAuth2ResourceServerConfigurer ->
                httpSecurityOAuth2ResourceServerConfigurer.jwt(Customizer.withDefaults()))
        .build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(List.of(allowedOrigin)); // Replace with your frontend origin
    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE")); // Allowed methods
    configuration.setAllowedHeaders(List.of("*")); // Allowed headers
    configuration.setAllowCredentials(true); // Allow cookies
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
