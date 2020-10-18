package com.emanuelvictor.api.nonfunctional.authengine.infrastructure.token;

import com.emanuelvictor.api.nonfunctional.authengine.domain.entities.Token;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

public abstract class AbstractToken implements IToken {

    /**
     *
     */
    public static final Logger LOGGER = LoggerFactory.getLogger(Token.class);

    @Setter
    private LocalDateTime createdOn;

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

    /**
     * @param value String
     */
    public AbstractToken(final String value) {
        this.createdOn = LocalDateTime.now();
        this.value = value;
    }

    /**
     * @return Optional<IToken>
     */
    public Optional<IToken> getNext() {
        return Optional.ofNullable(next);
    }

    /**
     * @return Optional<IToken>
     */
    public Optional<IToken> getPrevious() {
        return Optional.ofNullable(previous);
    }

    /**
     * @param next IToken
     * @return Optional<IToken>
     */
    public Optional<IToken> add(final IToken next) {

        if (this.getValue().equals(next.getValue())) {
//            LOGGER.info("Token already exits for this session");
            return Optional.of(this);
        }

        this.getNext().ifPresentOrElse(present -> present.add(next), () -> {
            next.setPrevious(this);
            this.setNext(next);
        });

        return Optional.of(next);
    }

    // ------------- Revoke

    /**
     *
     */
    @Override
    public Optional<IToken> revoke() {
        this.revokePrevious();
        if (!this.isRevoked()) {
            this.setRevoked(true);
            LOGGER.warn("Token with value " + this.getValue() + " revoked");
        }
        return this.revokeNext();
    }

    /**
     *
     */
    @Override
    public Optional<IToken> revokeNext() {
        return this.getNext().flatMap(IToken::revoke);
    }

    /**
     *
     */
    @Override
    public void revokePrevious() {
        this.getPrevious().ifPresent(present -> {
            if (!present.isRevoked())
                present.revoke();
        });
    }


    //  ------------- Print

    /**
     *
     */
    @Override
    public void print() {
        if (getPrevious().isPresent())
            System.out.print(" --> ");
        else {
            System.out.println(" ---------- ");
            System.out.println();
        }
        System.out.print(this.getValue());
        if (getNext().isEmpty()) {
            System.out.println();
            System.out.println();
            System.out.println(" ---------- ");
        }
        this.printNext();
    }

    /**
     *
     */
    @Override
    public void printFromRoot() {
        this.getRoot().orElseThrow().print();
    }

    /**
     *
     */
    @Override
    public void printNext() {
        this.getNext().ifPresent(IToken::print);
    }


    // ------------- Find

    /**
     * @param value String
     * @return IToken
     */
    @Override
    public Optional<IToken> findByValue(final String value) {

        if (this.getValue().equals(value))
            return Optional.of(this);

        return this.getRoot().orElseThrow().recursiveFindByValue(value);

    }

    /**
     * @param value String
     * @return Optional<IToken>
     */
    @Override
    public Optional<IToken> recursiveFindByValue(final String value) {

        if (this.getValue().equals(value))
            return Optional.of(this);

        if (this.getNext().isPresent()) //TODO
            return this.getNext().orElseThrow().recursiveFindByValue(value);

        return Optional.empty();
    }

    /**
     * Is not cover by tests TODO
     *
     * @return IToken
     */
    @Override
    public Optional<IToken> getRoot() {
        if (this.getPrevious().isEmpty())
            return Optional.of(this);
        else {
            return this.getPrevious().orElseThrow().getRoot();
        }
    }

    /**
     * Is not cover by tests TODO
     *
     * @return IToken
     */
    @Override
    public Optional<IToken> getLeaf() {
        if (this.getNext().isEmpty()) //TODO
            return Optional.of(this);
        else {
            return this.getNext().orElseThrow().getLeaf();
        }
    }

    // ------------- Count

    /**
     * @return int
     */
    @Override
    public int count() {
        return this.getRoot().orElseThrow().count(0);
    }

    /**
     * @param count int
     * @return int
     */
    @Override
    public int count(int count) {
        count++;
        if (this.getNext().isPresent()) {
            count = this.getNext().orElseThrow().count(count);
        }
        return count;
    }

    @Override
    public Optional<IToken> getAccess() {
        return this.getLeaf().orElseThrow().getPrevious();
    }

    @Override
    public Optional<IToken> getRefresh() {
        return this.getLeaf();
    }

    @Override
    public LocalDateTime getCreatedOn() {
        return this.createdOn;
    }
}
