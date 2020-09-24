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


import com.emanuelvictor.api.nonfunctional.authengine.application.security.custom.model.AccessTokenAuthentication;
import com.emanuelvictor.api.nonfunctional.authengine.application.security.custom.model.RefreshTokenAuthentication;
import com.emanuelvictor.api.nonfunctional.authengine.domain.entities.GrantType;
import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.DefaultExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.approval.Approval;
import org.springframework.security.oauth2.provider.approval.Approval.ApprovalStatus;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
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
public class JwtTokenStore implements TokenStore {

    private final JwtAccessTokenConverter jwtTokenEnhancer;

    private ApprovalStore approvalStore;

    /**
     *
     */
    @Getter
    private final List<AccessTokenAuthentication> accessTokens = new ArrayList<>();

    /**
     *
     */
    private final List<RefreshTokenAuthentication> refreshTokens = new ArrayList<>();

    /**
     * Create a JwtTokenStore with this token enhancer (should be shared with the DefaultTokenServices if used).
     *
     * @param jwtTokenEnhancer JwtAccessTokenConverter
     */
    public JwtTokenStore(final JwtAccessTokenConverter jwtTokenEnhancer) {
        this.jwtTokenEnhancer = jwtTokenEnhancer;
    }

    @Override
    public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
        return readAuthentication(token.getValue());
    }

    @Override
    public OAuth2Authentication readAuthentication(String token) {
        return jwtTokenEnhancer.extractAuthentication(jwtTokenEnhancer.decode(token));
    }

    @Override
    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        for (int i = 0; i < this.accessTokens.size(); i++)
            if (this.accessTokens.get(i).getToken().getValue().equals(token.getValue())) {
                if (authentication.getUserAuthentication() == null)
                    this.accessTokens.remove(i);
                else {
                    this.accessTokens.get(i).setAuthentication(authentication);
                    this.accessTokens.get(i).setToken(token);
                }
                return;
            }
        if (authentication.getUserAuthentication() != null)
            this.accessTokens.add(new AccessTokenAuthentication(token, authentication));
    }

    @Override
    public OAuth2AccessToken readAccessToken(String tokenValue) {
        OAuth2AccessToken accessToken = convertAccessToken(tokenValue);
        if (jwtTokenEnhancer.isRefreshToken(accessToken)) {
            throw new InvalidTokenException("Encoded token is a refresh token");
        }

        for (final AccessTokenAuthentication accessTokenAuthentication : this.accessTokens) {
            if (accessTokenAuthentication.getToken().getValue().equals(tokenValue))
                return accessTokenAuthentication.getToken();
        }

        return accessToken;
    }

    public OAuth2RefreshToken getRefreshTokenBySessionId(final String sessionId) {
        for (final RefreshTokenAuthentication refreshToken : this.refreshTokens)
            if (refreshToken.getAuthentication() != null && refreshToken.getAuthentication().getUserAuthentication() != null && refreshToken.getAuthentication().getUserAuthentication().getDetails() != null && refreshToken.getAuthentication().getUserAuthentication().getDetails() instanceof WebAuthenticationDetails && sessionId.equals(((WebAuthenticationDetails) refreshToken.getAuthentication().getUserAuthentication().getDetails()).getSessionId()))
                return refreshToken.getToken();
        return null;
    }


    public OAuth2AccessToken getAccessTokenBySessionId(final String sessionId) {
        for (final AccessTokenAuthentication acessToken : this.accessTokens)
            if (acessToken.getAuthentication() != null && acessToken.getAuthentication().getUserAuthentication() != null && acessToken.getAuthentication().getUserAuthentication().getDetails() != null && acessToken.getAuthentication().getUserAuthentication().getDetails() instanceof WebAuthenticationDetails && sessionId.equals(((WebAuthenticationDetails) acessToken.getAuthentication().getUserAuthentication().getDetails()).getSessionId()))
                return acessToken.getToken();
        return null;
    }

    private OAuth2AccessToken convertAccessToken(final String tokenValue) {
        return jwtTokenEnhancer.extractAccessToken(tokenValue, jwtTokenEnhancer.decode(tokenValue));
    }

    @Override
    public void removeAccessToken(final OAuth2AccessToken token) {
        for (int i = 0; i < this.accessTokens.size(); i++)
            if (this.accessTokens.get(i).getToken().getValue().equals(token.getValue())) {
                this.accessTokens.remove(i);
                break;
            }
    }

    @Override
    public void storeRefreshToken(final OAuth2RefreshToken token, final OAuth2Authentication authentication) {
        for (int i = 0; i < this.refreshTokens.size(); i++)
            if (this.refreshTokens.get(i).getToken().getValue().equals(token.getValue())) {
                if (authentication.getUserAuthentication() == null)
                    this.refreshTokens.remove(i);
                else {
                    this.refreshTokens.get(i).setAuthentication(authentication);
                    this.refreshTokens.get(i).setToken(token);
                }
                return;
            }
        if (authentication.getUserAuthentication() != null)
            this.refreshTokens.add(new RefreshTokenAuthentication(token, authentication));
    }

    @Override
    public OAuth2RefreshToken readRefreshToken(String tokenValue) {
        OAuth2AccessToken encodedRefreshToken = convertAccessToken(tokenValue);
        OAuth2RefreshToken refreshToken = createRefreshToken(encodedRefreshToken);
        if (approvalStore != null) {
            OAuth2Authentication authentication = readAuthentication(tokenValue);
            if (authentication.getUserAuthentication() != null) {
                String userId = authentication.getUserAuthentication().getName();
                String clientId = authentication.getOAuth2Request().getClientId();
                Collection<Approval> approvals = approvalStore.getApprovals(userId, clientId);
                Collection<String> approvedScopes = new HashSet<>();
                for (Approval approval : approvals) {
                    if (approval.isApproved()) {
                        approvedScopes.add(approval.getScope());
                    }
                }
                if (!approvedScopes.containsAll(authentication.getOAuth2Request().getScope())) {
                    return null;
                }
            }
        }

        for (final RefreshTokenAuthentication refreshhToken : this.refreshTokens) {
            if (refreshhToken.getToken().getValue().equals(refreshToken.getValue()))
                return refreshhToken.getToken();
        }

        return refreshToken;

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
    public void removeRefreshToken(final OAuth2RefreshToken token) {
        for (int i = 0; i < this.refreshTokens.size(); i++)
            if (this.refreshTokens.get(i).getToken().getValue().equals(token.getValue())) {
                this.refreshTokens.remove(i);
                break;
            }
        remove(token.getValue());
    }

    @Override
    public void removeAccessTokenUsingRefreshToken(final OAuth2RefreshToken token) {
        for (int i = 0; i < this.accessTokens.size(); i++)
            if (this.accessTokens.get(i).getToken().getRefreshToken() != null && this.accessTokens.get(i).getToken().getRefreshToken().getValue().equals(token.getValue())) {
                remove(this.accessTokens.get(i).getToken().getValue());
                this.accessTokens.remove(i);
                return;
            }

        RefreshTokenAuthentication refreshTokenAuthentication = null;
        for (final RefreshTokenAuthentication refreshToken : this.refreshTokens)
            if (refreshToken.getToken().getValue().equals(token.getValue())) {
                refreshTokenAuthentication = refreshToken;
                break;
            }

        if (refreshTokenAuthentication != null && refreshTokenAuthentication.getAuthentication() != null && refreshTokenAuthentication.getAuthentication().getUserAuthentication() != null && refreshTokenAuthentication.getAuthentication().getUserAuthentication().getDetails() != null && refreshTokenAuthentication.getAuthentication().getUserAuthentication().getDetails() instanceof WebAuthenticationDetails && ((WebAuthenticationDetails) refreshTokenAuthentication.getAuthentication().getUserAuthentication().getDetails()).getSessionId() != null)
            for (int i = 0; i < this.accessTokens.size(); i++)
                if (this.accessTokens.get(i).getAuthentication() != null && this.accessTokens.get(i).getAuthentication().getUserAuthentication() != null && this.accessTokens.get(i).getAuthentication().getUserAuthentication().getDetails() != null && this.accessTokens.get(i).getAuthentication().getUserAuthentication().getDetails() instanceof WebAuthenticationDetails && ((WebAuthenticationDetails) refreshTokenAuthentication.getAuthentication().getUserAuthentication().getDetails()).getSessionId().equals(((WebAuthenticationDetails) this.accessTokens.get(i).getAuthentication().getUserAuthentication().getDetails()).getSessionId())) {
                    remove(this.accessTokens.get(i).getToken().getValue());
                    this.accessTokens.remove(i);
                    break;
                }

    }

    public OAuth2AccessToken getAccessTokenUsingRefreshToken(final OAuth2RefreshToken token) {
        for (final AccessTokenAuthentication accessToken : this.accessTokens)
            if (accessToken.getToken().getRefreshToken() != null && accessToken.getToken().getRefreshToken().getValue().equals(token.getValue())) {
                return accessToken.getToken();
            }

        RefreshTokenAuthentication refreshTokenAuthentication = null;
        for (final RefreshTokenAuthentication refreshToken : this.refreshTokens)
            if (refreshToken.getToken().getValue().equals(token.getValue())) {
                refreshTokenAuthentication = refreshToken;
                break;
            }

        if (refreshTokenAuthentication != null && refreshTokenAuthentication.getAuthentication() != null && refreshTokenAuthentication.getAuthentication().getUserAuthentication() != null && refreshTokenAuthentication.getAuthentication().getUserAuthentication().getDetails() != null && refreshTokenAuthentication.getAuthentication().getUserAuthentication().getDetails() instanceof WebAuthenticationDetails && ((WebAuthenticationDetails) refreshTokenAuthentication.getAuthentication().getUserAuthentication().getDetails()).getSessionId() != null)
            for (final AccessTokenAuthentication accessToken : this.accessTokens)
                if (accessToken.getAuthentication() != null && accessToken.getAuthentication().getUserAuthentication() != null && accessToken.getAuthentication().getUserAuthentication().getDetails() != null && accessToken.getAuthentication().getUserAuthentication().getDetails() instanceof WebAuthenticationDetails && ((WebAuthenticationDetails) refreshTokenAuthentication.getAuthentication().getUserAuthentication().getDetails()).getSessionId().equals(((WebAuthenticationDetails) accessToken.getAuthentication().getUserAuthentication().getDetails()).getSessionId())) {
                    return accessToken.getToken();
                }

        return null;
    }


    @Override
    public OAuth2AccessToken getAccessToken(final OAuth2Authentication authentication) {
        if (authentication.getOAuth2Request().getGrantType() != null && authentication.getOAuth2Request().getGrantType().equals(GrantType.AUTHORIZATION_CODE.getValue()))
            if (authentication.getUserAuthentication() != null && authentication.getUserAuthentication().getDetails() != null)
                if (authentication.getUserAuthentication().getDetails() instanceof WebAuthenticationDetails)
                    if (((WebAuthenticationDetails) authentication.getUserAuthentication().getDetails()).getSessionId() != null)
                        for (final AccessTokenAuthentication accessToken : this.accessTokens)
                            if (accessToken.getAuthentication() != null && accessToken.getAuthentication().getUserAuthentication() != null && accessToken.getAuthentication().getUserAuthentication().getDetails() != null && accessToken.getAuthentication().getUserAuthentication().getDetails() instanceof WebAuthenticationDetails)
                                if (((WebAuthenticationDetails) authentication.getUserAuthentication().getDetails()).getSessionId().equals(((WebAuthenticationDetails) accessToken.getAuthentication().getUserAuthentication().getDetails()).getSessionId()))
                                    return accessToken.getToken();
        //TODO colocar outros grantypes
        return null;
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(final String clientId, final String userName) {
        return Collections.emptySet(); //TODO
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientId(final String clientId) {
        final Set<OAuth2AccessToken> accessTokensToReturn = new HashSet<>();
        for (final AccessTokenAuthentication accessToken : this.accessTokens)
            if (accessToken.getAuthentication() != null && accessToken.getAuthentication().getOAuth2Request() != null && accessToken.getAuthentication().getOAuth2Request().getClientId() != null && accessToken.getAuthentication().getOAuth2Request().getClientId().equals(clientId))
                accessTokensToReturn.add(accessToken.getToken());
        return accessTokensToReturn;
    }

    private void remove(final String token) {
        if (approvalStore != null) {
            final OAuth2Authentication auth = readAuthentication(token);
            final String clientId = auth.getOAuth2Request().getClientId();
            final Authentication user = auth.getUserAuthentication();
            if (user != null) {
                final Collection<Approval> approvals = new ArrayList<>();
                for (final String scope : auth.getOAuth2Request().getScope()) {
                    approvals.add(new Approval(user.getName(), clientId, scope, new Date(), ApprovalStatus.APPROVED));
                }
                approvalStore.revokeApprovals(approvals);
            }
        }
    }
}
