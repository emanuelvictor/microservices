package com.emanuelvictor.api.nonfunctional.authengine.domain.entities.token;

import lombok.Data;

import java.util.Optional;

@Data
public abstract class AbstractToken implements IToken {

    public Optional<IToken> next = Optional.empty();

    protected Optional<IToken> previous = Optional.empty();

    protected boolean revoked = false;

    protected String value;

    public AbstractToken(final String value) {
        this.value = value;
    }

    @Override
    public void setPrevious(final Optional<IToken> previous) {
        this.previous = previous;
    }
}
