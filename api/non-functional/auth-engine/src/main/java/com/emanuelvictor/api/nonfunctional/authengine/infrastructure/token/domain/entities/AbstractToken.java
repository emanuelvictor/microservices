package com.emanuelvictor.api.nonfunctional.authengine.infrastructure.token.domain.entities;

import com.emanuelvictor.api.nonfunctional.authengine.infrastructure.token.application.converters.JwtAccessTokenConverter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
// TODO it's no need be abstract. It's can bee Token.
@JsonIgnoreProperties({"next", "previous", "root", "leaf", "refresh", "access", "all"})
public abstract class AbstractToken implements IToken {

    /**
     *
     */
    public static final Logger LOGGER = LoggerFactory.getLogger(AbstractToken.class);

    /**
     *
     */
    @Setter
    private LocalDateTime createdOn;

    /**
     *
     */
    @Setter
    private IToken next;

    /**
     *
     */
    @Setter
    private IToken previous;

    /**
     *
     */
    @Getter
    @Setter(AccessLevel.PRIVATE)
    private boolean revoked = false;

    /**
     *
     */
    @Getter
    @Setter
    private String name;

    /**
     *
     */
    @Getter
    @Setter(AccessLevel.PACKAGE)
    private String value;

    /**
     * @param value String
     */
    public AbstractToken(final String value) {
        this.createdOn = LocalDateTime.now();
        this.value = value;

        this.name = extractNameFromToken(value);
    }

    /**
     * @param token String
     * @return String
     */
    public static String extractNameFromToken(final String token) {
        if (token != null)
            try {
                return JwtAccessTokenConverter.getInstance().extractAuthentication(JwtAccessTokenConverter.getInstance().decode(token)).getName(); // TODO the JwtAccessTokenConverter is needed only to this.
            } catch (final Exception ignored) {
            }
        return token;
    }

    /**
     *
     */
    @Override
    public void extractNameFromToken() {
        this.name = extractNameFromToken(this.value);
        this.getRoot().orElseThrow().setName(this.name);
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

        this.getNext().ifPresentOrElse(present -> {
            present.add(next);
            present.extractNameFromToken();
        }, () -> {
            next.setPrevious(this);
            this.setNext(next);
            this.extractNameFromToken();
        });

        return Optional.of(next);
    }

    // ------------- Revoke

    /**
     * @return Optional<IToken>
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
     * @return Optional<IToken>
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
     * @return Optional<IToken>
     */
    @Override
    public Optional<IToken> findByValue(final String value) {

        if (this.getValue().equals(value))
            return Optional.of(this);

        if (this.getNext().isPresent())
            return this.getNext().orElseThrow().findByValue(value);

        return Optional.empty();

    }

    // ------------- Retrieve

    /**
     * @return Optional<IToken>
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
     * @return Optional<IToken>
     */
    @Override
    public Optional<IToken> getLeaf() {
        if (this.getNext().isEmpty())
            return Optional.of(this);
        else {
            return this.getNext().orElseThrow().getLeaf();
        }
    }


    /**
     * @return Optional<IToken> the last access token
     */
    @Override
    public Optional<IToken> getAccess() {
        return this.getLeaf().orElseThrow().getPrevious();
    }

    /**
     * @return Optional<IToken> the last refresh token
     */
    @Override
    public Optional<IToken> getRefresh() {
        return this.getLeaf();
    }

    // ------------- Count

    /**
     * @return int
     */
    @Override
    public int count() {
        return this.getAll().size();
    }

    // ------------- Created On

    /**
     * @return LocalDateTime
     */
    @Override
    public LocalDateTime getCreatedOn() {
        return this.createdOn;
    }

    // ------------- getAll

    /**
     * @return Set<IToken>
     */
    public Set<IToken> getAll() {
        return this.getRoot().orElseThrow().getAll(new HashSet<>());
    }

    /**
     * TODO must be cover in tests
     *
     * @param tokens Set<IToken>
     * @return Set<IToken>
     */
    public Set<IToken> getAll(final Set<IToken> tokens) {

        tokens.add(this);

        getNext().ifPresent(next -> next.getAll(tokens));

        return tokens;

    }

    /**
     * TODO must be cover in tests
     *
     * @return boolean
     */
    public boolean isRoot() {
        return this.value.equals(this.getRoot().orElseThrow().getValue());
    }

    // ------------- Hashcode and equals

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractToken that = (AbstractToken) o;

        return value != null ? value.equals(that.value) : that.value == null;
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
