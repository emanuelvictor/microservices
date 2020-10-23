package com.emanuelvictor.api.nonfunctional.authengine.infrastructure.token.domain.repositories;

import com.emanuelvictor.api.nonfunctional.authengine.domain.entities.GrantType;
import com.emanuelvictor.api.nonfunctional.authengine.infrastructure.token.application.converters.JwtAccessTokenConverter;
import com.emanuelvictor.api.nonfunctional.authengine.domain.entities.Token;
import com.emanuelvictor.api.nonfunctional.authengine.infrastructure.token.domain.entities.IToken;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.common.*;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public abstract class AbstractTokenRepository implements ITokenRepository {

    /**
     *
     */
    public static final Logger LOGGER = LoggerFactory.getLogger(AbstractTokenRepository.class);

    /**
     *
     */
    private final Set<IToken> tokens = new HashSet<>();

    /**
     *
     */
    @Getter
    private final JwtAccessTokenConverter jwtAccessTokenConverter;

    /**
     * Create a JwtTokenStore with this token enhancer (should be shared with the CustomTokenServices if used).
     *
     * @param jwtAccessTokenConverter JwtAccessTokenConverter
     */
    public AbstractTokenRepository(final JwtAccessTokenConverter jwtAccessTokenConverter) {
        this.jwtAccessTokenConverter = jwtAccessTokenConverter;
    }


    /**
     * Create several and return the root
     *
     * @param tokensValueToCreate String
     * @return Optional<IToken>
     */
    public Optional<IToken> save(final String... tokensValueToCreate) {

        if (tokensValueToCreate == null)
            throw new RuntimeException("Token value to create must be not null");

        // Create the root token
        final Optional<IToken> root = this.save(tokensValueToCreate[0]);

        // Run the others token and add then
        for (final String tokenToCreate : tokensValueToCreate) {
            this.save(root.orElseThrow().getValue(), tokenToCreate);
        }

        return this.findTokenByValue(root.orElseThrow().getValue());
    }


    /**
     * @param tokenValueToFind   String
     * @param tokenValueToCreate String
     */
    private void save(final String tokenValueToFind, final String tokenValueToCreate) {

        if (tokenValueToFind == null)
            throw new RuntimeException("Token value to find must be not null");

        // Verify if the token to create alaredy exists
        this.findTokenByValue(tokenValueToCreate).ifPresentOrElse(iToken -> {
            LOGGER.warn("Token with value: " + iToken.getValue() + " already found");
        }, () -> this.findTokenByValue(tokenValueToFind).ifPresentOrElse(iToken -> iToken.add(new Token(tokenValueToCreate)), () -> {
            this.save(tokenValueToFind);
            this.save(tokenValueToFind, tokenValueToCreate);
        }));

        this.findTokenByValue(tokenValueToCreate);
    }

    /**
     * @param tokenValueToCreate String
     * @return IToken
     */
    private Optional<IToken> save(final String tokenValueToCreate) {

        this.findTokenByValue(tokenValueToCreate)
                .ifPresentOrElse(iToken -> LOGGER.warn(("Token already exists")), () -> this.tokens.add(new Token(tokenValueToCreate)));

        return this.findTokenByValue(tokenValueToCreate);
    }

    /**
     * @param tokenValue String
     * @return Optional<IToken>
     */
    public Optional<IToken> findTokenByValue(final String tokenValue) {

        for (final IToken iToken : this.tokens) {
            final Optional<IToken> found = iToken.findByValue(tokenValue);
            if (found.isPresent())
                return found;
        }

        return Optional.empty();
    }

    /**
     * @param tokenValue String
     * @return Optional<IToken>
     */
    @Override
    public Optional<IToken> revoke(final String tokenValue) {
        final Optional<IToken> token = this.findTokenByValue(tokenValue);

        token.ifPresentOrElse(IToken::revoke, () -> {
            throw new RuntimeException("Token with value: " + tokenValue + " not found");
        });

        return token;
    }

    /**
     * @return Set<IToken>
     */
    @Override
    public Set<IToken> findAll() {
        return this.tokens;
    }

// Is the following code is Legacy

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
        return jwtAccessTokenConverter.extractAuthentication(jwtAccessTokenConverter.decode(token));
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
        if (jwtAccessTokenConverter.isRefreshToken(accessToken)) {
            throw new InvalidTokenException("Encoded token is a refresh token");
        }

        return accessToken;
    }

    private OAuth2AccessToken convertAccessToken(final String tokenValue) {
        return jwtAccessTokenConverter.extractAccessToken(tokenValue, jwtAccessTokenConverter.decode(tokenValue));
    }

    @Override
    public void removeAccessToken(final OAuth2AccessToken oAuth2AccessToken) {
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
        extractSessionID(authentication).ifPresentOrElse(root -> {
            this.save(root, tokenValueToCreate);
        }, () -> {
// TDOO
        });
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
        if (!jwtAccessTokenConverter.isRefreshToken(encodedRefreshToken)) {
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
        revoke(oAuth2RefreshToken.getValue());
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

        return null; //TODO
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


    /**
     * @param authentication OAuth2Authentication
     * @return Optional<String>
     */
    public static Optional<String> extractSessionID(final OAuth2Authentication authentication) {
        if (authentication.getUserAuthentication() != null && authentication.getUserAuthentication().getDetails() != null)
            if (authentication.getUserAuthentication().getDetails() instanceof WebAuthenticationDetails)
                return Optional.ofNullable(((WebAuthenticationDetails) authentication.getUserAuthentication().getDetails()).getSessionId());
        return Optional.empty();
    }
}
