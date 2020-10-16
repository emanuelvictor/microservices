package com.emanuelvictor.api.nonfunctional.authengine.infrastructure.token.repositories;

import com.emanuelvictor.api.nonfunctional.authengine.application.security.custom.JwtTokenStore;
import com.emanuelvictor.api.nonfunctional.authengine.domain.entities.Token;
import com.emanuelvictor.api.nonfunctional.authengine.infrastructure.token.IToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Repository
public abstract class AbstractTokenRepository implements ITokenRepository {

    /**
     *
     */
    public static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenStore.class);

    /**
     *
     */
    private final Set<IToken> tokens = new HashSet<>();


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

        return root;
    }


    /**
     * @param tokenValueToFind   String
     * @param tokenValueToCreate String
     * @return Optional<IToken>
     */
    public Optional<IToken> save(final String tokenValueToFind, final String tokenValueToCreate) {

        if (tokenValueToFind == null)
            throw new RuntimeException("Token value to find must be not null");

        // Verify if the token to create alaredy exists
        this.findTokenByValue(tokenValueToCreate).ifPresentOrElse(iToken -> {
            LOGGER.info("Token with value: " + iToken.getValue() + " already found");
        }, () -> this.findTokenByValue(tokenValueToFind).ifPresentOrElse(iToken -> iToken.add(new Token(tokenValueToCreate)), () -> {
            this.save(tokenValueToFind);
            this.save(tokenValueToFind, tokenValueToCreate);
        }));

        return this.findTokenByValue(tokenValueToCreate);
    }

    /**
     * @param tokenValueToCreate String
     * @return IToken
     */
    public Optional<IToken> save(final String tokenValueToCreate) {

        final IToken token = new Token(tokenValueToCreate);

        this.findTokenByValue(tokenValueToCreate)
                .ifPresentOrElse(iToken -> LOGGER.info(("Token already exists")), () -> this.tokens.add(token));

        return Optional.of(token);
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

    @Override
    public Set<IToken> findAll() {
        return this.tokens;
    }
}
