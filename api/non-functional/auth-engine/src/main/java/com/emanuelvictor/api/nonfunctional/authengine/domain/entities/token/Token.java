package com.emanuelvictor.api.nonfunctional.authengine.domain.entities.token;


import java.util.Optional;

/**
 * "Composite"
 */
public class Token extends AbstractToken {

    /**
     * @param token String
     */
    public Token(final String token) {
        super(token);
    }

    /**
     * @param next
     * @return
     */
    public Optional<IToken> add(final Optional<IToken> next) {

        this.next.ifPresentOrElse(present -> {
            present.add(next);
        }, () -> {
            next.orElseThrow().setPrevious(Optional.of(this));
            this.next = next;
        });

        return next;
//        if (this.next.isPresent()) {
//            return this.next.add(next);
//        } else {
//            next.setPrevious(this);
//            this.next = next;
//            return this.next;
//        }
    }

    // ------------- Revoke

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
        this.next.ifPresent(IToken::revoke);
    }

    /**
     *
     */
    @Override
    public void revokePrevious() {
        this.previous.ifPresent(present -> {
            if (!present.isRevoked())
                present.revoke();
        });
    }

    //  ------------- Print

    /**
     *
     */
    @Override
    public void printPrevious() {
        this.previous.ifPresentOrElse(IToken::printPrevious, this::print);
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
        this.next.ifPresent(IToken::print);
    }


    // ------------- Find

    /**
     * @param value String
     * @return IToken
     */
    @Override
    public Optional<IToken> findByValue(final String value) {

        if (this.value.equals(value))
            return Optional.of(this);


        return this.getRoot().orElseThrow().recursiveFindByValue(value);

    }

    @Override
    public Optional<IToken> recursiveFindByValue(final String value) {


        if (this.value.equals(value))
            return Optional.of(this);

        if (this.next.isPresent()) //TODO
            return this.next.orElseThrow().recursiveFindByValue(value);

        return Optional.empty();
    }

    /**
     * @return IToken
     */
    @Override
    public Optional<IToken> getRoot() {
        if (this.previous.isEmpty()) //TODO
            return Optional.of(this);
        else {
            return this.previous.orElseThrow().getRoot();
        }
    }
}
