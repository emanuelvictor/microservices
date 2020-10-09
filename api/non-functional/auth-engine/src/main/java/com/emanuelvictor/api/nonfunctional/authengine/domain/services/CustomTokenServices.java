package com.emanuelvictor.api.nonfunctional.authengine.domain.services;

import com.emanuelvictor.api.nonfunctional.authengine.application.security.custom.JwtAccessTokenConverter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;


/**
 *
 */
//@Service
public class CustomTokenServices extends AbstractTokenServices {

    /**
     * @param tokenStore            TokenStore
     * @param clientDetailsService  ClientDetailsService
     * @param accessTokenEnhancer   JwtAccessTokenConverter
     */
    public CustomTokenServices(final TokenStore tokenStore,
                                  final ClientDetailsService clientDetailsService,
                                  final JwtAccessTokenConverter accessTokenEnhancer) {
        super(tokenStore, clientDetailsService, accessTokenEnhancer);
    }

}
