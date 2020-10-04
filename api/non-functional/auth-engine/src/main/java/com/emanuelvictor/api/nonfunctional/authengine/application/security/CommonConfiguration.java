package com.emanuelvictor.api.nonfunctional.authengine.application.security;

import com.emanuelvictor.api.nonfunctional.authengine.domain.services.CustomTokenServices;
import com.emanuelvictor.api.nonfunctional.authengine.application.security.custom.JwtAccessTokenConverter;
import com.emanuelvictor.api.nonfunctional.authengine.application.security.custom.JwtTokenStore;
import com.emanuelvictor.api.nonfunctional.authengine.domain.entities.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.provider.refresh.RefreshTokenGranter;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 2.0.0, 20/02/2020
 */
@Configuration
public class CommonConfiguration {

    private final static String DEFAULT_KEY = "integrator";
    private final static String DEFAULT_TOKEN_ENHANCER = DEFAULT_KEY;

    /**
     * @return TokenStore
     */
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    /**
     * @return JwtAccessTokenConverter
     */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(DEFAULT_KEY);
        return converter;
    }

//    /**
//     * @return CustomTokenServices
//     */
//    @Bean
//    @Primary
//    public CustomTokenServices tokenServices() {
//        final CustomTokenServices customTokenServices = new CustomTokenServices();
//        customTokenServices.setTokenStore(tokenStore()); ok
//        customTokenServices.setSupportRefreshToken(true); ok
//        customTokenServices.setTokenEnhancer(tokenEnhancer()); //TODO non necessary
//        return customTokenServices;
//    }

    /**
     * TokenEnhancer
     * Equivalent a salt from token
     *
     * @return TokenEnhancer
     */
    @Bean
    public TokenEnhancer tokenEnhancer() {

        return (accessToken, authentication) -> {

            final Map<String, Object> additionalInfo = new HashMap<>();

            if (authentication.getUserAuthentication() != null) {
                final User user = (User) authentication.getUserAuthentication().getPrincipal();
                additionalInfo.put("name", user.getName());
                additionalInfo.put("id", user.getId());
            }

            if (authentication.getOAuth2Request() != null && authentication.getOAuth2Request().getGrantType() != null)
                additionalInfo.put("grant_type", authentication.getOAuth2Request().getGrantType());

            additionalInfo.put(DEFAULT_TOKEN_ENHANCER, authentication.getName() + randomAlphabetic(4));
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
            return accessToken;
        };
    }

    /**
     * @return Validator
     */
    @Bean
    public Validator configureValidator() {
        return new LocalValidatorFactoryBean();
    }

    /**
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(12);
    }

}
