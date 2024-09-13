package com.emanuelvictor.api.nonfunctional.authengine.infrastructure.token.domain.entities;

import com.emanuelvictor.api.nonfunctional.authengine.domain.entities.GrantType;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

public interface IToken {

    Optional<IToken> revoke();

    Optional<IToken> revokeNext();

    void revokePrevious();

    boolean isRevoked();

    Optional<IToken> add(final IToken token);

    void print();

    void printFromRoot();

    void printNext();

    void setPrevious(final IToken token);

    Optional<IToken> getPrevious();

    Optional<IToken> findByValue(final String value);

    Optional<IToken> getNext();

    String getValue();

    Optional<IToken> getRoot();

    Optional<IToken> getLeaf();

    int count();

    /**
     * @return Optional<IToken> the last access token
     */
    Optional<IToken> getAccess();

    /**
     * @return Optional<IToken> the last refresh token
     */
    Optional<IToken> getRefresh();

    LocalDateTime getCreatedOn();

    Set<IToken> getAll();

    Set<IToken>  getAll(final Set<IToken> tokens);

    boolean isRoot();

    String getName();

    void setName(final String name);

    void extractNameFromToken();
}
