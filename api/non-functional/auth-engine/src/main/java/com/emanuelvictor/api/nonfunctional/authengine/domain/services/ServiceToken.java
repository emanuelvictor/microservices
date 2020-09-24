package com.emanuelvictor.api.nonfunctional.authengine.domain.services;

import com.emanuelvictor.api.nonfunctional.authengine.domain.entities.Client;
import com.emanuelvictor.api.nonfunctional.authengine.domain.repositories.feign.IClientFeignRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author emanuelvictor
 */
@Service
@RequiredArgsConstructor
public class ServiceToken {

    private final Logger LOG = LoggerFactory.getLogger(ServiceToken.class);

    /**
     *
     */
    private final TokenStore tokenStore;

    /**
     *
     */
    private final OAuth2RestTemplate oAuth2RestTemplate;

    /**
     *
     */
    private final IClientFeignRepository clientFeignRepository;

    /**
     * @param accessToken String
     */
    public void removeAccessToken(final String accessToken) {

        final OAuth2AccessToken oAuth2AccessToken = this.tokenStore.readAccessToken(accessToken);

        this.revoke(oAuth2AccessToken.getValue());

        tokenStore.removeAccessToken(oAuth2AccessToken);

        LOG.info("Refresh Token " + accessToken + " revoked ");
    }

    /**
     * @param refreshToken String
     */
    public void removeRefreshToken(final String refreshToken) {

        final OAuth2RefreshToken oAuth2RefreshToken = this.tokenStore.readRefreshToken(refreshToken);

        this.revoke(oAuth2RefreshToken.getValue());

        tokenStore.removeRefreshToken(oAuth2RefreshToken);

        LOG.info("Refresh Token " + refreshToken + " revoked ");
    }

    /**
     * @param token String
     */
    public void revoke(final String token) {

        final OAuth2Authentication oAuth2Authentication = tokenStore.readAuthentication(token);

        final Set<String> clients = extractClientsId(oAuth2Authentication);

        clients.forEach(clientString -> {
            final Optional<Client> clientOptional = this.clientFeignRepository.loadClientByClientId(clientString);
            clientOptional.ifPresentOrElse(client -> {
                if (client.getRevokeTokenUrl() != null) {
                    // Get the access token from authentication
                    oAuth2RestTemplate.delete(client.getRevokeTokenUrl() + "/" + token);
                }
            }, () -> {
                // todo HAS NO CLIENT FOR THIS client id, must throws exception
            });
        });
    }

    /**
     * @param oAuth2Authentication OAuth2Authentication
     * @return Set<String>
     */
    private static Set<String> extractClientsId(final OAuth2Authentication oAuth2Authentication) {
        Objects.requireNonNull(oAuth2Authentication);
        return extractClientsId(oAuth2Authentication.getUserAuthentication());
    }

    /**
     * @param authentication Authentication
     * @return Set<String>
     */
    private static Set<String> extractClientsId(final Authentication authentication) {
        Objects.requireNonNull(authentication);
        return extractClientsId(authentication.getAuthorities());
    }

    /**
     * @param grantedAuthorities Collection<? extends GrantedAuthority>
     * @return Set<String>
     */
    public static Set<String> extractClientsId(final Collection<? extends GrantedAuthority> grantedAuthorities) {
        Objects.requireNonNull(grantedAuthorities);
        return grantedAuthorities.stream()
                .map(GrantedAuthority::getAuthority)
                .map(authority -> authority.contains("/") ? authority.substring(authority.indexOf("/") + 1) : authority).map(authority -> authority.contains("/") ? authority.substring(0, authority.indexOf("/")) : authority)
                .filter(authority -> !authority.equals("root"))
                .collect(Collectors.toSet());
    }

    public void removeTokens(final String queryString) {
        final String refreshToken = queryString.substring(queryString.indexOf("&refresh_token=") + 15, queryString.length());
        final String accessToken = queryString.substring(queryString.indexOf("=") + 1, queryString.indexOf("&refresh_token="));

        this.removeRefreshToken(refreshToken);
        this.removeAccessToken(accessToken);
    }
}
