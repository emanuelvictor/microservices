package com.emanuelvictor.api.nonfunctional.authengine.domain.repositories.impl;

import com.emanuelvictor.api.nonfunctional.authengine.infrastructure.token.application.converters.JwtAccessTokenConverter;
import com.emanuelvictor.api.nonfunctional.authengine.infrastructure.token.domain.repositories.AbstractTokenRepository;

public class TokenRepository extends AbstractTokenRepository {
    /**
     * Create a JwtTokenStore with this token enhancer (should be shared with the CustomTokenServices if used).
     *
     * @param jwtTokenEnhancer JwtAccessTokenConverter
     */
    public TokenRepository(final JwtAccessTokenConverter jwtTokenEnhancer) {
        super(jwtTokenEnhancer);
    }
}
