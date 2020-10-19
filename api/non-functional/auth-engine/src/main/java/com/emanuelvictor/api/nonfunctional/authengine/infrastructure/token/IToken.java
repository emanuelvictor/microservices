package com.emanuelvictor.api.nonfunctional.authengine.infrastructure.token;

import java.time.LocalDateTime;
import java.util.Optional;

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

    int count(int count);

    /**
     * @return Optional<IToken> the last access token
     */
    Optional<IToken> getAccess();

    /**
     * @return Optional<IToken> the last refresh token
     */
    Optional<IToken> getRefresh();

    LocalDateTime getCreatedOn();
}
