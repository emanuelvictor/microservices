package com.emanuelvictor.api.nonfunctional.authengine.infrastructure.token;

import java.util.Optional;

public interface IToken {

    // TODO Must be return void
    Optional<IToken> revoke();

    // TODO Must be return void
    Optional<IToken> revokeNext();

    void revokePrevious();

    boolean isRevoked();

    Optional<IToken> add(final IToken token);

    void print();

    void printFromRoot();

    void printNext();

    void setPrevious(final IToken token);

    Optional<IToken> getPrevious();

    Optional<IToken> recursiveFindByValue(final String value);

    Optional<IToken> findByValue(final String value);

    Optional<IToken> getNext();

    String getValue();

    void setValue(final String value);

    Optional<IToken> getRoot();

    Optional<IToken> getLeaf();

    int count();

    int count(int count);
}
