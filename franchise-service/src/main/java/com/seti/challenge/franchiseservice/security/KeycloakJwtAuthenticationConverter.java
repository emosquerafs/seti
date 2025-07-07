package com.seti.challenge.franchiseservice.security;

import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

public class KeycloakJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    @Override
    public AbstractAuthenticationToken convert(@NotNull Jwt source) {

        return new JwtAuthenticationToken(
                source,
                Stream.concat(
                                new JwtGrantedAuthoritiesConverter().convert(source).stream(),
                                extractResourceRoles(source).stream())
                        .collect(toSet()));
    }

    @SuppressWarnings("unchecked")
    public Collection<? extends GrantedAuthority> extractResourceRoles(Jwt jwt) {
        try {
            Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
            if (resourceAccess == null) {
                return List.of(); // Retorna una lista vacía si no hay resource_access
            }

            var resourceAccessMap = new HashMap<>(resourceAccess);
            var PentaFolk = (Map<String, List<String>>) resourceAccessMap.get("SETI");
            if (PentaFolk == null) {
                return List.of(); // Retorna una lista vacía si no hay cuenta
            }

            var roles = PentaFolk.get("roles");
            if (roles == null) {
                return List.of(); // Retorna una lista vacía si no hay roles
            }

            return roles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role.replace("-", "_")))
                    .collect(toSet());
        } catch (Exception e) {
            // En caso de error, retorna una lista vacía de autoridades
            return List.of();
        }
    }
}