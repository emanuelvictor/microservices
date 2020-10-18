package com.emanuelvictor.api.nonfunctional.authengine.domain.services;

import com.emanuelvictor.api.nonfunctional.authengine.application.security.custom.JwtAccessTokenConverter;
import com.emanuelvictor.api.nonfunctional.authengine.application.security.custom.JwtTokenStore;
import com.emanuelvictor.api.nonfunctional.authengine.infrastructure.token.IToken;
import com.emanuelvictor.api.nonfunctional.authengine.infrastructure.token.repositories.ITokenRepository;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.*;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.InvalidScopeException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.*;

import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;


/**
 * Base implementation for token services using random UUID values for the access token and refresh token values. The
 * main extension point for customizations is the {@link TokenEnhancer} which will be called after the access and
 * refresh tokens have been generated but before they are stored.
 * <p>
 * Persistence is delegated to a {@code TokenStore} implementation and customization of the access token to a
 * {@link TokenEnhancer}.
 *
 * @author Ryan Heaton
 * @author Luke Taylor
 * @author Dave Syer
 */
public class AbstractTokenServices implements AuthorizationServerTokenServices, ResourceServerTokenServices, ConsumerTokenServices {

    protected static final int REFRESH_TOKEN_VALIDITY_SECONDS = 60 * 60 * 24 * 30; // default 30 days.
    protected static final int ACCESS_TOKEN_VALIDITY_SECONDS = 60 * 60 * 12; // default 12 hours.
//    protected static final boolean REUSE_REFRESH_TOKEN = true;

    /**
     *
     */
    protected final TokenStore tokenStore;

    /**
     *
     */
    protected final ITokenRepository tokenRepository;

    /**
     *
     */
    protected final ClientDetailsService clientDetailsService;

    /**
     *
     */
    protected final JwtAccessTokenConverter accessTokenEnhancer;

    /**
     * @param tokenStore           TokenStore
     * @param clientDetailsService ClientDetailsService
     * @param accessTokenEnhancer  JwtAccessTokenConverter
     */
    public AbstractTokenServices(final TokenStore tokenStore,
                                 final ITokenRepository tokenRepository,
                                 final ClientDetailsService clientDetailsService,
                                 final JwtAccessTokenConverter accessTokenEnhancer) {
        this.tokenStore = tokenStore;
        this.tokenRepository = tokenRepository;
        this.clientDetailsService = clientDetailsService;
        this.accessTokenEnhancer = accessTokenEnhancer;
    }

    /**
     * @param authentication OAuth2Authentication
     * @return OAuth2AccessToken
     * @throws AuthenticationException
     */
    @Override
    public OAuth2AccessToken createAccessToken(final OAuth2Authentication authentication) throws AuthenticationException {

        return JwtTokenStore.extractSessionID(authentication)
                .flatMap(root -> this.tokenRepository.findTokenByValue(root).map(iToken -> {

                    // Token is revoked, that is the logout has effected
                    if (iToken.isRevoked())
                        throw new RuntimeException("Token revoked");

                    final DefaultOAuth2AccessToken defaultOAuth2AccessToken = new DefaultOAuth2AccessToken(readAccessToken(iToken.getAccess().orElseThrow().getValue()));
                    final int refreshTokenValiditySeconds = getRefreshTokenValiditySeconds(authentication.getOAuth2Request());
                    if (refreshTokenValiditySeconds > 0) {
                        final DefaultExpiringOAuth2RefreshToken defaultExpiringOAuth2RefreshToken = new DefaultExpiringOAuth2RefreshToken(iToken.getRefresh().orElseThrow().getValue(),
                                new Date(iToken.getRefresh().orElseThrow().getCreatedOn().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() + (refreshTokenValiditySeconds * 1000L)));
                        defaultOAuth2AccessToken.setRefreshToken(defaultExpiringOAuth2RefreshToken);
                    }

                    // Return the access token if it was found and is not expired
                    if (defaultOAuth2AccessToken.isExpired()) {
                        // Create access token with refresh token associated
                        final OAuth2AccessToken accessToken = createAccessTokenWithRefreshToken(authentication);
                        // Save de root (or jsessionid), access token and refresh token
                        this.tokenRepository.save(root, accessToken.getValue(), accessToken.getRefreshToken().getValue()).orElseThrow().printFromRoot();
                        return accessToken;
                    } else {
                        return defaultOAuth2AccessToken;
                    }

                    // If not found the jsessionid in repository
                }).or(() -> {

                    // Create access token with refresh token associated
                    final OAuth2AccessToken accessToken = createAccessTokenWithRefreshToken(authentication);
                    // Save de root (or jsessionid), access token and refresh token
                    this.tokenRepository.save(root, accessToken.getValue(), accessToken.getRefreshToken().getValue()).orElseThrow().printFromRoot();
                    return Optional.of((DefaultOAuth2AccessToken) accessToken);

                    // If the request is not a authorization code, that is no have jsessionid
                })).or(() -> {

                    // Create access token with refresh token associated
                    final OAuth2AccessToken accessToken = createAccessTokenWithRefreshToken(authentication);
                    // Save access token and refresh token
                    this.tokenRepository.save(accessToken.getValue(), accessToken.getRefreshToken().getValue()).orElseThrow().printFromRoot();
                    return Optional.of((DefaultOAuth2AccessToken) accessToken);

                }).orElseThrow();

    }

    @Override
    public OAuth2AccessToken refreshAccessToken(final String refreshTokenValue, final TokenRequest tokenRequest) throws AuthenticationException {

        final IToken iToken = tokenRepository.findTokenByValue(refreshTokenValue).orElseThrow();

        final OAuth2Authentication authentication = tokenStore.readAuthentication(iToken.getAccess().orElseThrow().getValue());
//        authentication = createRefreshedAuthentication(authentication, tokenRequest);
        final DefaultOAuth2AccessToken defaultOAuth2AccessToken = new DefaultOAuth2AccessToken(createAccessTokenWithRefreshToken(authentication));
        final int refreshTokenValiditySeconds = getRefreshTokenValiditySeconds(authentication.getOAuth2Request());
        if (refreshTokenValiditySeconds > 0) {
            final DefaultExpiringOAuth2RefreshToken defaultExpiringOAuth2RefreshToken = new DefaultExpiringOAuth2RefreshToken(defaultOAuth2AccessToken.getRefreshToken().getValue(),
                    new Date(iToken.getRefresh().orElseThrow().getCreatedOn().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() + (refreshTokenValiditySeconds * 1000L)));
            defaultOAuth2AccessToken.setRefreshToken(defaultExpiringOAuth2RefreshToken);
        }

        final String clientId = authentication.getOAuth2Request().getClientId();
        if (clientId == null || !clientId.equals(tokenRequest.getClientId())) {
            throw new InvalidGrantException("Wrong client for this refresh token: " + refreshTokenValue);
        }

        if (iToken.isRevoked())
            throw new InvalidTokenException("Invalid refresh token (revoked): " + iToken);

        if (isExpired(defaultOAuth2AccessToken.getRefreshToken()))
            throw new InvalidTokenException("Invalid refresh token (expired): " + defaultOAuth2AccessToken.getRefreshToken());

        if (this.tokenRepository.findTokenByValue(defaultOAuth2AccessToken.getValue()).isPresent() || this.tokenRepository.findTokenByValue(defaultOAuth2AccessToken.getRefreshToken().getValue()).isPresent())
            throw new RuntimeException("The access token and refresh token is already saved");

        // Save de root (or jsessionid), access token and refresh token
        this.tokenRepository.save(refreshTokenValue, defaultOAuth2AccessToken.getValue(), defaultOAuth2AccessToken.getRefreshToken().getValue()).orElseThrow().printFromRoot();
        return defaultOAuth2AccessToken;
    }

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
        return tokenStore.getAccessToken(authentication);
    }

    /**
     * Create a refreshed authentication.
     *
     * @param authentication The authentication.
     * @param request        The scope for the refreshed token.
     * @return The refreshed authentication.
     * @throws InvalidScopeException If the scope requested is invalid or wider than the original scope.
     */
    private OAuth2Authentication createRefreshedAuthentication(OAuth2Authentication authentication, TokenRequest request) {
        OAuth2Authentication narrowed = authentication;
        Set<String> scope = request.getScope();
        OAuth2Request clientAuth = authentication.getOAuth2Request().refresh(request);
        if (scope != null && !scope.isEmpty()) {
            Set<String> originalScope = clientAuth.getScope();
            if (originalScope == null || !originalScope.containsAll(scope)) {
                throw new InvalidScopeException("Unable to narrow the scope of the client authentication to " + scope
                        + ".", originalScope);
            } else {
                clientAuth = clientAuth.narrowScope(scope);
            }
        }
        narrowed = new OAuth2Authentication(clientAuth, authentication.getUserAuthentication());
        return narrowed;
    }

    /**
     * TODO For what is protected?
     *
     * @param refreshToken OAuth2RefreshToken
     * @return boolean
     */
    protected boolean isExpired(OAuth2RefreshToken refreshToken) {
        if (refreshToken instanceof ExpiringOAuth2RefreshToken) {
            ExpiringOAuth2RefreshToken expiringToken = (ExpiringOAuth2RefreshToken) refreshToken;
            return expiringToken.getExpiration() == null
                    || System.currentTimeMillis() > expiringToken.getExpiration().getTime();
        }
        return false;
    }

    @Override
    public OAuth2AccessToken readAccessToken(String accessToken) {
        return tokenStore.readAccessToken(accessToken);
    }

    @Override
    public OAuth2Authentication loadAuthentication(String accessTokenValue) throws AuthenticationException,
            InvalidTokenException {
        OAuth2AccessToken accessToken = tokenStore.readAccessToken(accessTokenValue);
        if (accessToken == null) {
            throw new InvalidTokenException("Invalid access token: " + accessTokenValue);
        } else if (accessToken.isExpired()) {
            tokenStore.removeAccessToken(accessToken);
            throw new InvalidTokenException("Access token expired: " + accessTokenValue);
        }

        OAuth2Authentication result = tokenStore.readAuthentication(accessToken);
        if (result == null) {
            // in case of race condition
            throw new InvalidTokenException("Invalid access token: " + accessTokenValue);
        }
        if (clientDetailsService != null) {
            String clientId = result.getOAuth2Request().getClientId();
            try {
                clientDetailsService.loadClientByClientId(clientId);
            } catch (ClientRegistrationException e) {
                throw new InvalidTokenException("Client not valid: " + clientId, e);
            }
        }
        return result;
    }

    @Override
    public boolean revokeToken(String tokenValue) {
        OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
        if (accessToken == null) {
            return false;
        }
        if (accessToken.getRefreshToken() != null) {
            tokenStore.removeRefreshToken(accessToken.getRefreshToken());
        }
        tokenStore.removeAccessToken(accessToken);
        return true;
    }

    private OAuth2RefreshToken createRefreshToken(OAuth2Authentication authentication) {
        int validitySeconds = getRefreshTokenValiditySeconds(authentication.getOAuth2Request());
        final String value = UUID.randomUUID().toString();
        if (validitySeconds > 0) {
            return new DefaultExpiringOAuth2RefreshToken(value, new Date(System.currentTimeMillis()
                    + (validitySeconds * 1000L)));
        }
        return new DefaultOAuth2RefreshToken(value);
    }

    private OAuth2AccessToken createAccessTokenWithRefreshToken(OAuth2Authentication authentication) {
        final DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(UUID.randomUUID().toString());
        int validitySeconds = getAccessTokenValiditySeconds(authentication.getOAuth2Request());
        if (validitySeconds > 0) {
            token.setExpiration(new Date(System.currentTimeMillis() + (validitySeconds * 1000L)));
        }
        token.setRefreshToken(createRefreshToken(authentication));
        token.setScope(authentication.getOAuth2Request().getScope());

        return accessTokenEnhancer != null ? accessTokenEnhancer.enhance(token, authentication) : token;
    }

    private OAuth2AccessToken createAccessToken(OAuth2Authentication authentication, OAuth2RefreshToken refreshToken) {
        DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(UUID.randomUUID().toString());
        int validitySeconds = getAccessTokenValiditySeconds(authentication.getOAuth2Request());
        if (validitySeconds > 0) {
            token.setExpiration(new Date(System.currentTimeMillis() + (validitySeconds * 1000L)));
        }
        token.setRefreshToken(refreshToken);
        token.setScope(authentication.getOAuth2Request().getScope());

        return accessTokenEnhancer != null ? accessTokenEnhancer.enhance(token, authentication) : token;
    }

    /**
     * TODO For what is protected?
     * The access token validity period in seconds
     *
     * @param clientAuth the current authorization request
     * @return the access token validity period in seconds
     */
    protected int getAccessTokenValiditySeconds(OAuth2Request clientAuth) {
        if (clientDetailsService != null) {
            ClientDetails client = clientDetailsService.loadClientByClientId(clientAuth.getClientId());
            Integer validity = client.getAccessTokenValiditySeconds();
            if (validity != null) {
                return validity;
            }
        }
        return ACCESS_TOKEN_VALIDITY_SECONDS;
    }

    /**
     * The refresh token validity period in seconds
     *
     * @param clientAuth the current authorization request
     * @return the refresh token validity period in seconds
     */
    protected int getRefreshTokenValiditySeconds(OAuth2Request clientAuth) {
        if (clientDetailsService != null) {
            ClientDetails client = clientDetailsService.loadClientByClientId(clientAuth.getClientId());
            Integer validity = client.getRefreshTokenValiditySeconds();
            if (validity != null) {
                return validity;
            }
        }
        return REFRESH_TOKEN_VALIDITY_SECONDS;
    }

}
