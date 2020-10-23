package com.emanuelvictor.api.nonfunctional.authengine.domain;


import com.emanuelvictor.api.nonfunctional.authengine.infrastructure.token.application.converters.JwtAccessTokenConverter;
import com.emanuelvictor.api.nonfunctional.authengine.domain.repositories.impl.TokenRepositoryImpl;
import com.emanuelvictor.api.nonfunctional.authengine.infrastructure.token.domain.repositories.ITokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import static com.emanuelvictor.api.nonfunctional.authengine.application.security.CommonConfiguration.DEFAULT_KEY;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

@RunWith(JUnitPlatform.class)
public abstract class AbstractsTests {

    /**
     *
     */
    protected ITokenRepository tokenStore;

    /**
     *
     */
//    @Before before to junit
    @BeforeEach
    public void beforeTests() {
        tokenStore = new TokenRepositoryImpl(this.accessTokenConverter());
    }

    /**
     * @return JwtAccessTokenConverter
     */
    public JwtAccessTokenConverter accessTokenConverter() {
        final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(DEFAULT_KEY);
        return converter;
    }
}
