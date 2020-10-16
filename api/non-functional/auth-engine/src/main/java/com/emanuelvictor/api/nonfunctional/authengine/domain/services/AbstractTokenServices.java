package com.emanuelvictor.api.nonfunctional.authengine.domain.services;

import com.emanuelvictor.api.nonfunctional.authengine.application.security.custom.JwtAccessTokenConverter;
import com.emanuelvictor.api.nonfunctional.authengine.application.security.custom.JwtTokenStore;
import com.emanuelvictor.api.nonfunctional.authengine.infrastructure.token.IToken;
import com.emanuelvictor.api.nonfunctional.authengine.infrastructure.token.repositories.ITokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.*;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.InvalidScopeException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

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
    protected static final boolean SUPPORT_REFRESH_TOKEN = true;
    protected static final boolean REUSE_REFRESH_TOKEN = true;

    /**
     *
     */
    protected final ITokenRepository tokenRepository;

    /**
     *
     */
    protected final TokenStore tokenStore;

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
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public OAuth2AccessToken createAccessToken(OAuth2Authentication authentication) throws AuthenticationException {

        return JwtTokenStore.extractSessionID(authentication)
                .flatMap(root -> this.tokenRepository.findTokenByValue(root).map(iToken -> {

                    // If token is not root
                    if (!iToken.getLeaf().orElseThrow().getValue().equals(root)) {

                        final DefaultOAuth2AccessToken defaultOAuth2AccessToken = new DefaultOAuth2AccessToken(iToken.getLeaf().orElseThrow().getPrevious().orElseThrow().getValue());
                        defaultOAuth2AccessToken.setRefreshToken(new DefaultOAuth2RefreshToken(iToken.getLeaf().orElseThrow().getValue()));

                        // Return the access token if it was found and is not expired
                        if (!defaultOAuth2AccessToken.isExpired())
                            return defaultOAuth2AccessToken;
                        else {
                            // TODO flux to refresh token
                            throw new RuntimeException("Token expired");
                        }

                        // If token is not root, a access token and refresh token must be created
                    } else {
                        // Create access token with refresh token associated
                        final OAuth2AccessToken accessToken = createAccessTokenWithRefreshToken(authentication);
                        // Save de root (or jsessionid), access token and refresh token
                        this.tokenRepository.save(root, accessToken.getValue(), accessToken.getRefreshToken().getValue()).orElseThrow().printFromRoot();
                        return accessToken;
                    }

                    // If not found the jsessionid in repository
                }).or(() -> {

                    // Create access token with refresh token associated
                    final OAuth2AccessToken accessToken = createAccessTokenWithRefreshToken(authentication);
                    // Save de root (or jsessionid), access token and refresh token
                    this.tokenRepository.save(root, accessToken.getValue(), accessToken.getRefreshToken().getValue()).orElseThrow().printFromRoot();
                    return Optional.of(accessToken);

                    // If the request is not a authorization code, that is no have jsessionid
                })).or(() -> {

                    // Create access token with refresh token associated
                    final OAuth2AccessToken accessToken = createAccessTokenWithRefreshToken(authentication);
                    // Save access token and refresh token
                    this.tokenRepository.save(accessToken.getValue(), accessToken.getRefreshToken().getValue()).orElseThrow().printFromRoot();
                    return Optional.of(accessToken);
                }).orElseThrow();


////         Only create a new refresh token if there wasn't an existing one
////         associated with an expired access token.
////         Clients might be holding existing refresh tokens, so we re-use it in
////         the case that the old access token
////         expired.
//        if (refreshToken == null) {
//            refreshToken = createRefreshToken(authentication);
//        }
//        // But the refresh token itself might need to be re-issued if it has
//        // expired.
//        else if (refreshToken instanceof ExpiringOAuth2RefreshToken) {
//            ExpiringOAuth2RefreshToken expiring = (ExpiringOAuth2RefreshToken) refreshToken;
//            if (System.currentTimeMillis() > expiring.getExpiration().getTime()) {
//                refreshToken = createRefreshToken(authentication);
//            }
//        }
//
//        OAuth2AccessToken accessToken = createAccessToken(authentication, refreshToken);
//
//        if (authentication.getUserAuthentication() != null) {
//            System.out.println("Session Token ->>> " + ((WebAuthenticationDetails) authentication.getUserAuthentication().getDetails()).getSessionId());
//            System.out.println("Acccess Token ->>> " + accessToken.getValue());
//            System.out.println("Refresh Token ->>> " + accessToken.getRefreshToken().getValue());
//        }
//
//        tokenStore.storeAccessToken(accessToken, authentication);
//        // In case it was modified
//        refreshToken = accessToken.getRefreshToken();
//        if (refreshToken != null) {
//            tokenStore.storeRefreshToken(refreshToken, authentication);
//        }
//        return accessToken;

    }

    /**
     * @param tokenValue String
     * @return String
     */
    public String getClientId(final String tokenValue) {
        OAuth2Authentication authentication = tokenStore.readAuthentication(tokenValue);
        if (authentication == null) {
            throw new InvalidTokenException("Invalid access token: " + tokenValue);
        }
        OAuth2Request clientAuth = authentication.getOAuth2Request();
        if (clientAuth == null) {
            throw new InvalidTokenException("Invalid access token (no client id): " + tokenValue);
        }
        return clientAuth.getClientId();
    }

    @Override
    public OAuth2AccessToken refreshAccessToken(String refreshTokenValue, TokenRequest tokenRequest)
            throws AuthenticationException {

        if (!SUPPORT_REFRESH_TOKEN) {
            throw new InvalidGrantException("Invalid refresh token: " + refreshTokenValue);
        }

        OAuth2RefreshToken refreshToken = tokenStore.readRefreshToken(refreshTokenValue);
        if (refreshToken == null) {
            throw new InvalidGrantException("Invalid refresh token: " + refreshTokenValue);
        }

        OAuth2Authentication authentication = tokenStore.readAuthenticationForRefreshToken(refreshToken);
        String clientId = authentication.getOAuth2Request().getClientId();
        if (clientId == null || !clientId.equals(tokenRequest.getClientId())) {
            throw new InvalidGrantException("Wrong client for this refresh token: " + refreshTokenValue);
        }

        // clear out any access tokens already associated with the refresh
        // token.
        tokenStore.removeAccessTokenUsingRefreshToken(refreshToken);

        if (isExpired(refreshToken)) {
            tokenStore.removeRefreshToken(refreshToken);
            throw new InvalidTokenException("Invalid refresh token (expired): " + refreshToken);
        }

        authentication = createRefreshedAuthentication(authentication, tokenRequest);

        if (!REUSE_REFRESH_TOKEN) {
            tokenStore.removeRefreshToken(refreshToken);
            refreshToken = createRefreshToken(authentication);
        }

        OAuth2AccessToken accessToken = createAccessToken(authentication, refreshToken);
        tokenStore.storeAccessToken(accessToken, authentication);
        if (!REUSE_REFRESH_TOKEN) {
            tokenStore.storeRefreshToken(accessToken.getRefreshToken(), authentication);
        }
        return accessToken;
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
        if (!isSupportRefreshToken(authentication.getOAuth2Request())) {
            return null;
        }
        int validitySeconds = getRefreshTokenValiditySeconds(authentication.getOAuth2Request());
        String value = UUID.randomUUID().toString();
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

    /**
     * TODO For what is protected?
     *
     * @param clientAuth the current authorization request
     * @return boolean to indicate if refresh token is supported
     */
    protected boolean isSupportRefreshToken(OAuth2Request clientAuth) {
        if (clientDetailsService != null) {
            ClientDetails client = clientDetailsService.loadClientByClientId(clientAuth.getClientId());
            return client.getAuthorizedGrantTypes().contains("refresh_token");
        }
        return SUPPORT_REFRESH_TOKEN;
    }

}
