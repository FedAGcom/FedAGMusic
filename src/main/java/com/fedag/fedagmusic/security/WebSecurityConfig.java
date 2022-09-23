package com.fedag.fedagmusic.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class WebSecurityConfig {
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;

    public WebSecurityConfig(AuthenticationManager authenticationManager, SecurityContextRepository securityContextRepository) {
        this.authenticationManager = authenticationManager;
        this.securityContextRepository = securityContextRepository;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }


    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
        return httpSecurity
                .exceptionHandling()
                .authenticationEntryPoint((serverWebExchange, e) ->
                        Mono.fromRunnable(
                                () -> serverWebExchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED)
                        )
                )
                .accessDeniedHandler((serverWebExchange, e) ->
                        Mono.fromRunnable(
                                () -> serverWebExchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN)
                        )
                )
                .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .authenticationManager(authenticationManager)
                .securityContextRepository(securityContextRepository)
                .authorizeExchange()
                .pathMatchers("/**", "/login", "/favicon.ico").permitAll()
                //TODO role
                //.pathMatchers("/users/**").hasRole("ADMIN")
                .pathMatchers("/api/v1/users").permitAll()
                .anyExchange()
                .authenticated()
                .and()
                .build();
    }
}
