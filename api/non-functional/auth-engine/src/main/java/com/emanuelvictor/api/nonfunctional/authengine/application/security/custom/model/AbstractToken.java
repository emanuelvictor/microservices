package com.emanuelvictor.api.nonfunctional.authengine.application.security.custom.model;

import lombok.Data;

@Data
public abstract class AbstractToken implements Token {

    protected Token parent;

    protected boolean revoked = false;

    protected String value;

    public AbstractToken(final String value) {
        this.value = value;
    }

    @Override
    public void setParent(Token parent) {
        this.parent = parent;
    }
}
