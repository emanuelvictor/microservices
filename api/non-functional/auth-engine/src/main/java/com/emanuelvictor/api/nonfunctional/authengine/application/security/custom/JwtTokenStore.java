package com.emanuelvictor.api.nonfunctional.authengine.application.security.custom;


/*
 * Copyright 2013-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */


import com.emanuelvictor.api.nonfunctional.authengine.domain.entities.GrantType;
import com.emanuelvictor.api.nonfunctional.authengine.domain.entities.token.IToken;
import com.emanuelvictor.api.nonfunctional.authengine.domain.entities.token.Token;
import com.emanuelvictor.api.nonfunctional.authengine.domain.repositories.impl.AbstractTokenStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.common.*;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import java.util.*;

/**
 * A {@link TokenStore} implementation that just reads data from the tokens themselves. Not really a store since it
 * never persists anything, and methods like {@link #getAccessToken(OAuth2Authentication)} always return null. But
 * nevertheless a useful tool since it translates access tokens to and from authentications. Use this wherever a
 * {@link TokenStore} is needed, but remember to use the same {@link JwtAccessTokenConverter} instance (or one with the same
 * verifier) as was used when the tokens were minted.
 *
 * @author Dave Syer
 */
public class JwtTokenStore extends AbstractTokenStore implements TokenStore {

    /**
     *
     */
    public static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenStore.class);

    private final JwtAccessTokenConverter jwtTokenEnhancer;

    /**
     *
     */
    private final Set<IToken> tokens = new HashSet<>();

    /**
     * Create a JwtTokenStore with this token enhancer (should be shared with the CustomTokenServices if used).
     *
     * @param jwtTokenEnhancer JwtAccessTokenConverter
     */
    public JwtTokenStore(final JwtAccessTokenConverter jwtTokenEnhancer) {
        this.jwtTokenEnhancer = jwtTokenEnhancer;
    }

    /**
     * @param token OAuth2AccessToken
     * @return OAuth2Authentication
     */
    @Override
    public OAuth2Authentication readAuthentication(final OAuth2AccessToken token) {
        return readAuthentication(token.getValue());
    }

    /**
     * @param token String
     * @return OAuth2Authentication
     */
    @Override
    public OAuth2Authentication readAuthentication(final String token) {
        return jwtTokenEnhancer.extractAuthentication(jwtTokenEnhancer.decode(token));
    }

    /**
     * It is only for authorization code. Only the grant type authorization code store the tokens
     *
     * @param oAuth2AccessToken OAuth2RefreshToken
     * @param authentication    OAuth2Authentication
     */
    @Override
    public void storeAccessToken(final OAuth2AccessToken oAuth2AccessToken, final OAuth2Authentication authentication) {
        storeToken(authentication, oAuth2AccessToken.getValue());
    }

    @Override
    public OAuth2AccessToken readAccessToken(String tokenValue) {
        OAuth2AccessToken accessToken = convertAccessToken(tokenValue);
        if (jwtTokenEnhancer.isRefreshToken(accessToken)) {
            throw new InvalidTokenException("Encoded token is a refresh token");
        }

        return accessToken;
    }

    private OAuth2AccessToken convertAccessToken(final String tokenValue) {
        return jwtTokenEnhancer.extractAccessToken(tokenValue, jwtTokenEnhancer.decode(tokenValue));
    }

    @Override
    public void removeAccessToken(final OAuth2AccessToken oAuth2AccessToken) {
        this.revoke(oAuth2AccessToken.getValue());
    }

    /**
     * It is only for authorization code. Only the grant type authorization code store the tokens
     *
     * @param oAuth2RefreshToken OAuth2RefreshToken
     * @param authentication     OAuth2Authentication
     */
    @Override
    public void storeRefreshToken(final OAuth2RefreshToken oAuth2RefreshToken, final OAuth2Authentication authentication) {
        storeToken(authentication, oAuth2RefreshToken.getValue());
    }

    /**
     * @param authentication     OAuth2Authentication
     * @param tokenValueToCreate String
     */
    private void storeToken(final OAuth2Authentication authentication, final String tokenValueToCreate) {
        if (authentication.getUserAuthentication() != null && authentication.getUserAuthentication().getDetails() != null)
            if (authentication.getUserAuthentication().getDetails() instanceof WebAuthenticationDetails)
                if (((WebAuthenticationDetails) authentication.getUserAuthentication().getDetails()).getSessionId() != null) {
                    final String root = ((WebAuthenticationDetails) authentication.getUserAuthentication().getDetails()).getSessionId();
                    this.create(root, tokenValueToCreate);
                }
    }

    /**
     * @param tokenValue String
     * @return OAuth2RefreshToken
     */
    @Override
    public OAuth2RefreshToken readRefreshToken(final String tokenValue) {
        final OAuth2AccessToken encodedRefreshToken = convertAccessToken(tokenValue);
        return createRefreshToken(encodedRefreshToken);
    }

    private OAuth2RefreshToken createRefreshToken(OAuth2AccessToken encodedRefreshToken) {
        if (!jwtTokenEnhancer.isRefreshToken(encodedRefreshToken)) {
            throw new InvalidTokenException("Encoded token is not a refresh token");
        }
        if (encodedRefreshToken.getExpiration() != null) {
            return new DefaultExpiringOAuth2RefreshToken(encodedRefreshToken.getValue(),
                    encodedRefreshToken.getExpiration());
        }
        return new DefaultOAuth2RefreshToken(encodedRefreshToken.getValue());
    }

    @Override
    public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
        return readAuthentication(token.getValue());
    }

    @Override
    public void removeRefreshToken(final OAuth2RefreshToken oAuth2RefreshToken) {
        this.revoke(oAuth2RefreshToken.getValue());
    }

    /**
     * @param oAuth2RefreshToken OAuth2RefreshToken
     */
    @Override
    public void removeAccessTokenUsingRefreshToken(final OAuth2RefreshToken oAuth2RefreshToken) {
        this.revoke(oAuth2RefreshToken.getValue());
    }

    // TODO Must be refactored
    @Override
    public OAuth2AccessToken getAccessToken(final OAuth2Authentication authentication) {
        if (authentication.getOAuth2Request().getGrantType() != null && authentication.getOAuth2Request().getGrantType().equals(GrantType.AUTHORIZATION_CODE.getValue()))
            if (authentication.getUserAuthentication() != null && authentication.getUserAuthentication().getDetails() != null)
                if (authentication.getUserAuthentication().getDetails() instanceof WebAuthenticationDetails)
                    if (((WebAuthenticationDetails) authentication.getUserAuthentication().getDetails()).getSessionId() != null) {
                        final String root = ((WebAuthenticationDetails) authentication.getUserAuthentication().getDetails()).getSessionId();
                        final Optional<IToken> toReturn = findTokenByValue(root);
                        if (toReturn.isPresent()) {
                            final OAuth2AccessToken accessToken = readAccessToken(toReturn.map(iToken -> iToken.getLeaf().orElseThrow().getPrevious().orElseThrow()).map(IToken::getValue).orElse(null));
                            if (accessToken.getRefreshToken() != null)
                                return accessToken;
                            else {
                                final OAuth2RefreshToken refreshToken = readRefreshToken(toReturn.map(iToken -> iToken.getLeaf().orElseThrow()).map(IToken::getValue).orElse(null));
                                final DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(accessToken);
                                token.setRefreshToken(refreshToken);
                                return token;
                            }
                        }
                    }

        return null;
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(final String clientId, final String userName) {
        return Collections.emptySet(); //TODO
    }

    // TODO must be refactored
    // todo verify necessity
    @Override
    public Collection<OAuth2AccessToken> findTokensByClientId(final String clientId) {
        return null;
    }
}
