package com.emanuelvictor.api.nonfunctional.authengine.domain.repositories.impl;

import com.emanuelvictor.api.nonfunctional.authengine.infrastructure.token.jwt.JwtAccessTokenConverter;
import com.emanuelvictor.api.nonfunctional.authengine.infrastructure.token.repositories.AbstractTokenRepository;

public class TokenRepositoryImpl extends AbstractTokenRepository {
    /**
     * Create a JwtTokenStore with this token enhancer (should be shared with the CustomTokenServices if used).
     *
     * @param jwtTokenEnhancer JwtAccessTokenConverter
     */
    public TokenRepositoryImpl(final JwtAccessTokenConverter jwtTokenEnhancer) {
        super(jwtTokenEnhancer);
    }
}
