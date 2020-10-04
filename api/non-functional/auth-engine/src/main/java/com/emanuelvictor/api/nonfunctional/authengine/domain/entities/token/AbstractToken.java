package com.emanuelvictor.api.nonfunctional.authengine.domain.entities.token;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

@Data
public abstract class AbstractToken implements Token {

    public Token next;

    protected Token previous;

    protected boolean revoked = false;

    protected String value;

    public AbstractToken(final String value) {
        this.value = value;
    }

    @Override
    public void setPrevious(final Token previous) {
        this.previous = previous;
    }
}
