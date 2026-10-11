package com.nihongo.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationConverter jwtAuthenticationConverter;

    public SecurityConfig(
            JwtAuthenticationConverter jwtAuthenticationConverter
    ) {
        this.jwtAuthenticationConverter =
                jwtAuthenticationConverter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, com.nihongo.security.RemoteSessionVerifier verifier, com.nihongo.security.BrowserOriginPolicy origins, com.nihongo.security.InternalCredentials credentials, org.springframework.core.env.Environment env)
            throws Exception {

        http

                .csrf(AbstractHttpConfigurer::disable)

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(HttpMethod.OPTIONS, "/**")
                        .permitAll()

                        .requestMatchers("/api/admin/**")
                        .hasRole("ADMIN")

                        .anyRequest()
                        .authenticated()
                )

                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(
                                        jwtAuthenticationConverter
                                )
                        )
                )

                .httpBasic(AbstractHttpConfigurer::disable)

                .formLogin(AbstractHttpConfigurer::disable);

        http.addFilterBefore(new com.nihongo.security.BrowserOriginServletFilter(origins,credentials,env.getProperty("security.origin-check.enabled",Boolean.class,true)),org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter.class);
        http.addFilterAfter(new com.nihongo.security.ServletSessionValidationFilter(verifier,env.getProperty("security.session-validation.enabled",Boolean.class,true)),org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter.class);
        return http.build();
    }
}