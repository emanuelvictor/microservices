package com.emanuelvictor.api.functional.accessmanager.domain.services;

import com.emanuelvictor.api.functional.accessmanager.application.spring.oauth.custom.JwtTokenStore;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;


/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 2.0.0, 04/01/2020
 */
@Service
@RequiredArgsConstructor
public class TokenService {

    private final Logger LOG = LoggerFactory.getLogger(TokenService.class);

    /**
     *
     */
    private final TokenStore tokenStore;

    /**
     * @param token String
     */
    public void revoke(final String token) {
        ((JwtTokenStore) this.tokenStore).revoke(token);
    }
}
