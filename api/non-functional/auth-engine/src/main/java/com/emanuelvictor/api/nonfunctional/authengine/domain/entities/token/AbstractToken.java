package com.emanuelvictor.api.nonfunctional.authengine.domain.entities.token;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

public abstract class AbstractToken implements IToken {

    @Setter
    private IToken next;

    @Setter
    private IToken previous;

    @Getter
    @Setter
    private boolean revoked = false;

    @Getter
    @Setter
    private String value;

    public AbstractToken(final String value) {
        this.value = value;
    }

    public Optional<IToken> getNext() {
        return Optional.ofNullable(next);
    }

    public Optional<IToken> getPrevious() {
        return Optional.ofNullable(previous);
    }

}
