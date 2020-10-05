package com.emanuelvictor.api.nonfunctional.authengine.domain.repositories;

import com.emanuelvictor.api.nonfunctional.authengine.domain.entities.token.IToken;

import java.util.Optional;

public interface ITokenStore {

    Optional<IToken> create(final String tokenValueToFind, final String tokenValueToCreate);

    Optional<IToken> create(final String tokenValueToCreate);

    Optional<IToken> findTokenByValue(final String tokenValue);

}
