package com.security.identity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.annotation.PostConstruct;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomOidcUserService customOidcUserService;

    public SecurityConfig(CustomOidcUserService customOidcUserService,
            CustomOAuth2UserService customOAuth2UserService) {
        this.customOidcUserService = customOidcUserService;
        this.customOAuth2UserService = customOAuth2UserService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(auth -> auth.requestMatchers("/public/**", "/", "/error")
                .permitAll().anyRequest().authenticated()).oauth2Login(
                        oAuth -> oAuth.userInfoEndpoint(userInfo -> userInfo.oidcUserService(customOidcUserService)
                                .userService(customOAuth2UserService)).defaultSuccessUrl("/api/success", true));
        return http.build();
    }

    @PostConstruct
    void checkService() {
        System.out.println("CustomOAuth2UserService bean loaded: " + customOidcUserService);
    }

}
