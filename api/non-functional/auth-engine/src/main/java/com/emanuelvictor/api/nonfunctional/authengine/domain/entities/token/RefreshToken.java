package com.emanuelvictor.api.nonfunctional.authengine.domain.entities.token;


import java.util.Objects;

/**
 * "Composite"
 */
public class RefreshToken extends AbstractToken {

    /**
     * @param token String
     */
    public RefreshToken(final String token) {
        super(token);
    }

    /**
     * @param next Token
     */
    public void add(final Token next) {
        Objects.requireNonNull(next);
        if (this.next != null)
            this.next.add(next);
        else {
            next.setPrevious(this);
            this.next = next;
        }
    }

    /**
     *
     */
    @Override
    public void revoke() {
        this.revokePrevious();
        if (!this.revoked) {
            this.revoked = true;
            System.out.println("Revoke token " + this.value);
        }
        this.revokeNext();
    }

    /**
     *
     */
    @Override
    public void revokeNext() {
        if (this.next != null)
            this.next.revoke();
    }

    /**
     *
     */
    @Override
    public void revokePrevious() {
        if (this.previous != null && !this.previous.isRevoked())
            this.previous.revoke();
    }

    /**
     *
     */
    @Override
    public void printPrevious() {
        if (this.previous != null)
            this.previous.printPrevious();
        else {
            this.print();
        }
    }

    /**
     *
     */
    @Override
    public void print() {
        System.out.println(value);
        this.printNext();
    }

    /**
     *
     */
    @Override
    public void printNext() {
        if (this.next != null)
            this.next.print();
    }

}
