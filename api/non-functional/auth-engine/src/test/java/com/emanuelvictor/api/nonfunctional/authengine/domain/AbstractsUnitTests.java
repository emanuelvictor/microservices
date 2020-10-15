package com.emanuelvictor.api.nonfunctional.authengine.domain;


import com.emanuelvictor.api.nonfunctional.authengine.application.security.custom.JwtAccessTokenConverter;
import com.emanuelvictor.api.nonfunctional.authengine.application.security.custom.JwtTokenStore;
import com.emanuelvictor.api.nonfunctional.authengine.domain.entities.User;
import com.emanuelvictor.api.nonfunctional.authengine.domain.repositories.ITokenStore;
import com.emanuelvictor.api.nonfunctional.authengine.domain.repositories.impl.AbstractTokenStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

import static com.emanuelvictor.api.nonfunctional.authengine.application.security.CommonConfiguration.DEFAULT_KEY;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

@RunWith(JUnitPlatform.class)
public abstract class AbstractsUnitTests {

    /**
     *
     */
    protected ITokenStore tokenStore;

    /**
     *
     */
//    @Before before to junit
    @BeforeEach
    public void beforeTests() {
        tokenStore = new JwtTokenStore(this.accessTokenConverter());
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
