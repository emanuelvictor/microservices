package com.emanuelvictor.api.nonfunctional.authengine.domain.repositories;

import com.emanuelvictor.api.nonfunctional.authengine.domain.entities.token.IToken;

import java.util.Optional;

public interface ITokenStore {

    public Optional<IToken> create(final String tokenValueToFind, final String tokenValueToCreate);

    public Optional<IToken> create(final String tokenValueToCreate);

    public Optional<IToken> findTokenByValue(final String tokenValue);

}
