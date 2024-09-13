package com.emanuelvictor.api.nonfunctional.authengine.domain;


import com.emanuelvictor.api.nonfunctional.authengine.infrastructure.token.application.converters.JwtAccessTokenConverter;
import com.emanuelvictor.api.nonfunctional.authengine.domain.repositories.impl.TokenRepository;
import com.emanuelvictor.api.nonfunctional.authengine.infrastructure.token.domain.repositories.ITokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;


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
        tokenStore = new TokenRepository();
    }

}
