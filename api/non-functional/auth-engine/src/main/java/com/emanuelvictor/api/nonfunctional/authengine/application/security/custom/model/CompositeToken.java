package com.emanuelvictor.api.nonfunctional.authengine.application.security.custom.model;

import java.util.ArrayList;

/**
 * "Composite"
 */
public class CompositeToken extends AbstractToken {
    //Collection of child tokens.
    private final ArrayList<Token> childTokens = new ArrayList<>();

    public CompositeToken(String token) {
        super(token);
    }

    //Adds the token to the composition.
    public void add(Token token) {
        token.setParent(this);
        childTokens.add(token);
    }

    //Prints the token.
    @Override
    public void revoke() {
        this.revokeParent();
        if (!this.revoked) {
            this.revoked = true;
            System.out.println("Revoke token " + this.value);
        }
        this.revokeChilds();
    }

    @Override
    public void revokeChilds() {
        for (final Token token : childTokens) {
            token.revoke();  //Delegation
        }
    }

    @Override
    public void revokeParent() {
        if (this.parent != null && !this.parent.isRevoked())
            this.parent.revoke();
    }
}
