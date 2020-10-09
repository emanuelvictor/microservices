package com.emanuelvictor.api.nonfunctional.authengine.domain.repositories.impl;

import com.emanuelvictor.api.nonfunctional.authengine.domain.entities.token.IToken;
import com.emanuelvictor.api.nonfunctional.authengine.domain.entities.token.Token;
import com.emanuelvictor.api.nonfunctional.authengine.domain.repositories.ITokenStore;

import java.util.*;

public class ITokenStoreImpl implements ITokenStore {

    /**
     *
     */
    private final Set<IToken> tokens = new HashSet<>();

    /**
     * @param tokenValueToFind String
     * @param tokenValueToCreate String
     * @return Optional<IToken>
     */
    public Optional<IToken> create(final String tokenValueToFind, final String tokenValueToCreate) {

        if (tokenValueToFind == null)
            throw new RuntimeException("Token value to find must be not null");

        // Token to save and return if saved
        final IToken token = new Token(tokenValueToCreate);

        // Verify if the token to create alaredy exists
        this.findTokenByValue(tokenValueToCreate).ifPresentOrElse(iToken -> {
            throw new RuntimeException("Token with value: " + iToken.getValue() + " already founded");
        }, () -> this.findTokenByValue(tokenValueToFind).ifPresentOrElse(iToken -> iToken.add(token), () -> {
            throw new RuntimeException("Token with value: " + tokenValueToFind + " not found");
        }));

        return Optional.of(token);
    }

    /**
     * @param tokenValueToCreate String
     * @return IToken
     */
    public Optional<IToken> create(final String tokenValueToCreate) {

        final IToken token = new Token(tokenValueToCreate);

        this.findTokenByValue(tokenValueToCreate).ifPresentOrElse(iToken -> {
            throw new RuntimeException("Token already exists");
        }, () -> this.tokens.add(token));

        return Optional.of(token);
    }

    /**
     * @param tokenValue String
     * @return Optional<IToken>
     */
    public Optional<IToken> findTokenByValue(final String tokenValue) {

        for (final IToken iToken : this.tokens) {
            final Optional<IToken> founded = iToken.findByValue(tokenValue);
            if (founded.isPresent())
                return founded;
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
}
