package com.emanuelvictor.api.nonfunctional.authengine.domain.repositories;

import com.emanuelvictor.api.nonfunctional.authengine.domain.entities.token.IToken;

import java.util.Optional;
import java.util.Set;

public interface ITokenStore {


    Optional<IToken> create(final String tokenValueToCreate);

    Optional<IToken> create(final String tokenValueToFind, final String tokenValueToCreate);

    Optional<IToken> create(final String... tokenValueToCreate);

    Optional<IToken> create(final String tokenValueToFind, final String... tokenValueToCreate);

    Optional<IToken> findTokenByValue(final String tokenValue);

    // TODO Must be return void
    Optional<IToken> revoke(final String tokenValue);
}
