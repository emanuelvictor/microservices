//package com.emanuelvictor.api.functional.accessmanager.application.spring.oauth;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
//
///**
// * @author Emanuel Victor
// * @version 1.0.0
// * @since 2.0.0, 20/02/2020
// */
//@Configuration
//@EnableResourceServer
//@RequiredArgsConstructor
//public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
//
//    /**
//     *
//     */
//    private final ResourceServerTokenServices resourceServerTokenServices;
//
//    /**
//     * Configura a permissão das requisições
//     *
//     * @param http HttpSecurity
//     * @throws Exception
//     */
//    @Override
//    public void configure(final HttpSecurity http) throws Exception {
//        http.authorizeRequests().requestMatchers("/**").permitAll()
//                .anyRequest()
//                .authenticated();
////        http.authorizeRequests().antMatchers("/**").permitAll()
////                .anyRequest()
////                .authenticated();
//    }
//
//    /**
//     * @param config ResourceServerSecurityConfigurer
//     */
//    @Override
//    public void configure(final ResourceServerSecurityConfigurer config) {
//        config.tokenServices(resourceServerTokenServices);
//    }
//
//}
