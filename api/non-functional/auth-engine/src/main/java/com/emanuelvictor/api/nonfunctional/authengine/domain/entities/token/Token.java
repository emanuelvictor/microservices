package com.emanuelvictor.api.nonfunctional.authengine.domain.entities.token;

public interface Token {

    void revoke();

    void revokeNext();

    void revokePrevious();

    boolean isRevoked();

    void add(final Token token);

    void print();

    void printPrevious();

    void printNext();

    void setPrevious(Token token);

    Token getNext();
//    public Token getPrevious();
}
