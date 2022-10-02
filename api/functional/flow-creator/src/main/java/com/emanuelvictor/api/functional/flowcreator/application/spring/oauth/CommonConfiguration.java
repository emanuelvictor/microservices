//package com.emanuelvictor.api.functional.flowcreator.application.spring.oauth;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import com.emanuelvictor.api.functional.flowcreator.application.spring.oauth.custom.JwtAccessTokenConverter;
//import com.emanuelvictor.api.functional.flowcreator.application.spring.oauth.custom.JwtTokenStore;
//
///**
// * @author Emanuel Victor
// * @version 1.0.0
// * @since 2.0.0, 20/02/2020
// */
//@Configuration
//public class CommonConfiguration {
//
//    private final static String DEFAULT_KEY = "integrator";
//    private final static String DEFAULT_TOKEN_ENHANCER = DEFAULT_KEY;
//
//    /**
//     * @return TokenStore
//     */
//    @Bean
//    public TokenStore tokenStore() {
//        return new JwtTokenStore(accessTokenConverter());
//    }
//
//    /**
//     * @return JwtAccessTokenConverter
//     */
//    @Bean
//    public JwtAccessTokenConverter accessTokenConverter() {
//        final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//        converter.setSigningKey(DEFAULT_KEY);
//        return converter;
//    }
//
//    /**
//     * @return DefaultTokenServices
//     */
//    @Bean
//    @Primary
//    public DefaultTokenServices tokenServices() {
//        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
//        defaultTokenServices.setTokenStore(tokenStore());
//        defaultTokenServices.setSupportRefreshToken(true);
//        return defaultTokenServices;
//    }
//
//    /**
//     * @return PasswordEncoder
//     */
//    @Bean
//    public PasswordEncoder encoder() {
//        return new BCryptPasswordEncoder(12);
//    }
//
//}
