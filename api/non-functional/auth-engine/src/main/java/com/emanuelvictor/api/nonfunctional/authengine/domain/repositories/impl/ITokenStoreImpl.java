package com.emanuelvictor.api.nonfunctional.authengine.domain.repositories.impl;

import com.emanuelvictor.api.nonfunctional.authengine.domain.entities.token.IToken;
import com.emanuelvictor.api.nonfunctional.authengine.domain.entities.token.Token;
import com.emanuelvictor.api.nonfunctional.authengine.domain.repositories.ITokenStore;

import java.util.*;

public class ITokenStoreImpl implements ITokenStore {


    private Set<IToken> tokens = new HashSet<>();

    /**
     * @param tokenValueToFind String
     * @return IToken
     */
    public Optional<IToken> create(final String tokenValueToFind, final String tokenValueToCreate) {

        final Optional<IToken> token = Optional.of(new Token(tokenValueToCreate));

        this.findTokenByValue(tokenValueToFind).ifPresentOrElse(iToken -> iToken.add(token), () -> this.create(tokenValueToCreate));

        return this.findTokenByValue(tokenValueToCreate);
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

        return this.tokens.stream()
                .map(iToken -> iToken.findByValue(tokenValue))
                .filter(Objects::nonNull)
                .findAny().orElse(Optional.empty());

    }
}
