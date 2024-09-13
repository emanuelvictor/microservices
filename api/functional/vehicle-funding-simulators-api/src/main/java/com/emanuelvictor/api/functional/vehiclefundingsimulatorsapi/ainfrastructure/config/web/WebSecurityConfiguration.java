package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.ainfrastructure.config.web;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * TODO TODO TOD NEED ADJUSTS TO WORK GATEWAY API
 *
 * @version 1.0.0
 * @since 2.0.0, 31/01/2020
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration {


//    /**
//     * {@inheritDoc}
//     *
//     * @param http
//     * @throws Exception
//     */
//    @Override
//    protected void configure(final HttpSecurity http) throws Exception {
//        http.csrf()
//                .disable()
//                .authorizeRequests()
//                .anyRequest()
//                .authenticated();
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(
                authorizeHttpRequestsCustomizer -> authorizeHttpRequestsCustomizer.anyRequest().permitAll());
//        http.csrf()
//                .disable()
//                .authorizeRequests()
//                .anyRequest()
//                .authenticated();


        return http.build();
    }

}
