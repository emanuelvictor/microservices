package com.emanuelvictor.api.nonfunctional.authengine.infrastructure.token.repositories;

import com.emanuelvictor.api.nonfunctional.authengine.infrastructure.token.IToken;
import org.springframework.security.oauth2.provider.token.TokenStore;

import java.util.Optional;
import java.util.Set;

public interface ITokenRepository extends TokenStore {

    Set<IToken> findAll();

    Optional<IToken> save(final String... tokenValueToCreate);

    Optional<IToken> findTokenByValue(final String tokenValue);

    // TODO Must be return void
    Optional<IToken> revoke(final String tokenValue);
}
