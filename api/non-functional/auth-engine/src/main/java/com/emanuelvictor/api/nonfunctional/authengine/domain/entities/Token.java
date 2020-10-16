package com.emanuelvictor.api.nonfunctional.authengine.domain.entities;


import com.emanuelvictor.api.nonfunctional.authengine.infrastructure.token.AbstractToken;

/**
 * "Composite"
 */
public class Token extends AbstractToken {

    /**
     * @param value Value
     */
    public Token(final String value) {
        super(value);
    }


}
