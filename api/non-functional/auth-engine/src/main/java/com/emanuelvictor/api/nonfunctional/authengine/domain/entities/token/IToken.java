package com.emanuelvictor.api.nonfunctional.authengine.domain.entities.token;

import java.util.Optional;

public interface IToken {

    void revoke();

    void revokeNext();

    void revokePrevious();

    boolean isRevoked();

    Optional<IToken> add(final Optional<IToken> token);

    void print();

    void printPrevious();

    void printNext();

    void setPrevious(final Optional<IToken> token);

    Optional<IToken> recursiveFindByValue(final String value);

    Optional<IToken> findByValue(final String value);

    Optional<IToken> getNext();

    String getValue();

    Optional<IToken> getRoot();

//    public Token getPrevious();
}
