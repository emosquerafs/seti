package com.seti.challenge.franchiseservice.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.server.resource.authentication.*;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.beans.factory.annotation.Value;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuerUri;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/swagger-ui.html",
                                "api/franchises",
                                "api/franchises/**",
                                "api/branches",
                                "api/branches/**",
                                "api/products/",
                                "api/products/**",
                                "/webjars/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
                        .pathMatchers("/actuator/**").permitAll()
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwtSpec -> jwtSpec
                                .jwtDecoder(jwtDecoder())
                                .jwtAuthenticationConverter(new ReactiveJwtAuthenticationConverterAdapter(
                                        new KeycloakJwtAuthenticationConverter()
                                ))
                        )
                );
        return http.build();
    }

    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        NimbusReactiveJwtDecoder jwtDecoder = NimbusReactiveJwtDecoder
                .withJwkSetUri(issuerUri + "/protocol/openid-connect/certs")
                .build();
        
        // Use standard validation for issuer and timestamps
        jwtDecoder.setJwtValidator(JwtValidators.createDefaultWithIssuer(issuerUri));
        return jwtDecoder;
    }
}
