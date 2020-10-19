package com.emanuelvictor.api.nonfunctional.authengine.domain.repositories;

import com.emanuelvictor.api.nonfunctional.authengine.domain.AbstractsTests;
import com.emanuelvictor.api.nonfunctional.authengine.infrastructure.token.IToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static com.emanuelvictor.api.nonfunctional.authengine.domain.entity.TokenTests.TOKEN_VALUES;

public class TokenRepositoryTests extends AbstractsTests {


    /**
     *
     */
    @Test
    public void findTokenByValueMustNotFound() {
        final String tokenValue = UUID.randomUUID().toString();
        Assertions.assertTrue(this.tokenStore.findTokenByValue(tokenValue).isEmpty());
    }


    /**
     *
     */
    @Test
    public void findTokenByValueMustFound() {
        final String tokenValueToCreate = UUID.randomUUID().toString();
        this.tokenStore.save(tokenValueToCreate);
        Assertions.assertTrue(this.tokenStore.findTokenByValue(tokenValueToCreate).isPresent());
    }

    /**
     *
     */
    @Test
    public void createToken() {
        final String tokenValueToFind = UUID.randomUUID().toString();
        final String tokenValueToCreate = UUID.randomUUID().toString();
        Assertions.assertTrue(this.tokenStore.findTokenByValue(tokenValueToCreate).isEmpty());
        this.tokenStore.save(tokenValueToFind);
        Assertions.assertTrue(this.tokenStore.findTokenByValue(tokenValueToFind).isPresent());
        this.tokenStore.save(tokenValueToFind, tokenValueToCreate);
        Assertions.assertTrue(this.tokenStore.findTokenByValue(tokenValueToCreate).isPresent());
    }

    /**
     *
     */
    @Test
    public void countTests() {

        final IToken firstToken = this.tokenStore.save(TOKEN_VALUES[1]).orElseThrow();
        this.tokenStore.save(TOKEN_VALUES[1], TOKEN_VALUES[2]);
        this.tokenStore.save(TOKEN_VALUES[2], TOKEN_VALUES[3]);
        this.tokenStore.save(TOKEN_VALUES[3], TOKEN_VALUES[4]);
        this.tokenStore.save(TOKEN_VALUES[4], TOKEN_VALUES[5]);

        // Original size
        final int size = firstToken.count();

        // The tokens must be have 5 tokens
        Assertions.assertEquals(5, size);

    }

    /**
     *
     */
    @Test
    public void createRepeatedTokenInThisSessionMustBeUpdate() {

        final IToken firstToken = this.tokenStore.save(TOKEN_VALUES[1]).orElseThrow();
        this.tokenStore.save(TOKEN_VALUES[1], TOKEN_VALUES[2]);
        this.tokenStore.save(TOKEN_VALUES[2], TOKEN_VALUES[3]);
        this.tokenStore.save(TOKEN_VALUES[3], TOKEN_VALUES[4]);
        this.tokenStore.save(TOKEN_VALUES[4], TOKEN_VALUES[5]);

        // Original size
        final int size = firstToken.count();

        // Create new token
        this.tokenStore.save(TOKEN_VALUES[2], TOKEN_VALUES[3]);

        // The tokens must be have 5 tokens
        Assertions.assertEquals(size, this.tokenStore.findTokenByValue(TOKEN_VALUES[4]).orElseThrow().count());

    }

    /**
     *
     */
    @Test
    public void createRepeatedTokenLikeOtherSessionMustPass() {

        this.tokenStore.save(TOKEN_VALUES[1]);
        this.tokenStore.save(TOKEN_VALUES[1], TOKEN_VALUES[2]);
        this.tokenStore.save(TOKEN_VALUES[2], TOKEN_VALUES[3]);
        this.tokenStore.save(TOKEN_VALUES[3], TOKEN_VALUES[4]);
        this.tokenStore.save(TOKEN_VALUES[4], TOKEN_VALUES[5]);

        this.tokenStore.save(TOKEN_VALUES[3]);

    }

    /**
     *
     */
    @Test
    public void createRepeatedTokenInnerOtherSessionBeUpdated() {

        this.tokenStore.save(TOKEN_VALUES[1]);
        this.tokenStore.save(TOKEN_VALUES[1], TOKEN_VALUES[2]);
        this.tokenStore.save(TOKEN_VALUES[2], TOKEN_VALUES[3]);
        this.tokenStore.save(TOKEN_VALUES[3], TOKEN_VALUES[4]);
        this.tokenStore.save(TOKEN_VALUES[4], TOKEN_VALUES[5]);

        final int firstSizeExpected = this.tokenStore.findTokenByValue(TOKEN_VALUES[3]).orElseThrow().count();

        this.tokenStore.save(TOKEN_VALUES[11]);
        this.tokenStore.save(TOKEN_VALUES[11], TOKEN_VALUES[12]);
        this.tokenStore.save(TOKEN_VALUES[12], TOKEN_VALUES[13]);
        this.tokenStore.save(TOKEN_VALUES[13], TOKEN_VALUES[14]);
        this.tokenStore.save(TOKEN_VALUES[14], TOKEN_VALUES[15]);

        final int secondSizeExpected = this.tokenStore.findTokenByValue(TOKEN_VALUES[11]).orElseThrow().count();

        // Create repeated token
        this.tokenStore.save(TOKEN_VALUES[15], TOKEN_VALUES[3]);

        Assertions.assertEquals(firstSizeExpected, this.tokenStore.findTokenByValue(TOKEN_VALUES[4]).orElseThrow().count());

        Assertions.assertEquals(secondSizeExpected, this.tokenStore.findTokenByValue(TOKEN_VALUES[14]).orElseThrow().count());

    }

    /**
     *
     */
    @Test
    public void createTokenWithAFakePreviousMustBeCreateTwoTokensInRoot() {
        this.tokenStore.save(TOKEN_VALUES[15], TOKEN_VALUES[13]);
        Assertions.assertEquals(2, this.tokenStore.findTokenByValue(TOKEN_VALUES[15]).orElseThrow().count());

        final IToken token = this.tokenStore.findTokenByValue(TOKEN_VALUES[15]).orElseThrow();
        Assertions.assertTrue(token.getPrevious().isEmpty());
        Assertions.assertTrue(token.getNext().isPresent());
        Assertions.assertEquals(TOKEN_VALUES[15], token.getValue());
        Assertions.assertEquals(TOKEN_VALUES[13], token.getNext().orElseThrow().getValue());
        Assertions.assertTrue(token.getNext().orElseThrow().getPrevious().isPresent());
        Assertions.assertTrue(token.getNext().orElseThrow().getNext().isEmpty());
    }

    /**
     *
     */
    @Test
    public void createTokenWithTokenToFindIsNullMustFail() {
        Assertions.assertThrows(java.lang.RuntimeException.class, () -> this.tokenStore.save(null, TOKEN_VALUES[13]));
    }

    /**
     *
     */
    @Test
    public void creatingLinkedTokensMustPass() {
        this.tokenStore.save(TOKEN_VALUES[1]);
        this.tokenStore.save(TOKEN_VALUES[1], TOKEN_VALUES[2]);
        this.tokenStore.save(TOKEN_VALUES[2], TOKEN_VALUES[3]);
        this.tokenStore.save(TOKEN_VALUES[3], TOKEN_VALUES[4]);
        this.tokenStore.save(TOKEN_VALUES[4], TOKEN_VALUES[5]);
        this.tokenStore.findTokenByValue(TOKEN_VALUES[3]).ifPresent(IToken::printFromRoot);

        Assertions.assertEquals(this.tokenStore.findTokenByValue(TOKEN_VALUES[2]).orElseThrow().getValue(), this.tokenStore.findTokenByValue(TOKEN_VALUES[1]).orElseThrow().getNext().orElseThrow().getValue());
        Assertions.assertEquals(this.tokenStore.findTokenByValue(TOKEN_VALUES[3]).orElseThrow().getValue(), this.tokenStore.findTokenByValue(TOKEN_VALUES[2]).orElseThrow().getNext().orElseThrow().getValue());
        Assertions.assertEquals(this.tokenStore.findTokenByValue(TOKEN_VALUES[4]).orElseThrow().getValue(), this.tokenStore.findTokenByValue(TOKEN_VALUES[3]).orElseThrow().getNext().orElseThrow().getValue());
        Assertions.assertEquals(this.tokenStore.findTokenByValue(TOKEN_VALUES[5]).orElseThrow().getValue(), this.tokenStore.findTokenByValue(TOKEN_VALUES[4]).orElseThrow().getNext().orElseThrow().getValue());

        this.tokenStore.save(TOKEN_VALUES[11]);
        this.tokenStore.save(TOKEN_VALUES[11], TOKEN_VALUES[12]);
        this.tokenStore.save(TOKEN_VALUES[12], TOKEN_VALUES[13]);
        this.tokenStore.save(TOKEN_VALUES[13], TOKEN_VALUES[14]);
        this.tokenStore.save(TOKEN_VALUES[14], TOKEN_VALUES[15]);
        this.tokenStore.save(TOKEN_VALUES[14], TOKEN_VALUES[18]);
        this.tokenStore.save(TOKEN_VALUES[12], TOKEN_VALUES[16]);
        this.tokenStore.save(TOKEN_VALUES[13], TOKEN_VALUES[17]);
        this.tokenStore.findTokenByValue(TOKEN_VALUES[13]).ifPresent(IToken::printFromRoot);

        Assertions.assertEquals(this.tokenStore.findTokenByValue(TOKEN_VALUES[12]).orElseThrow().getValue(), this.tokenStore.findTokenByValue(TOKEN_VALUES[11]).orElseThrow().getNext().orElseThrow().getValue());
        Assertions.assertEquals(this.tokenStore.findTokenByValue(TOKEN_VALUES[13]).orElseThrow().getValue(), this.tokenStore.findTokenByValue(TOKEN_VALUES[12]).orElseThrow().getNext().orElseThrow().getValue());
        Assertions.assertEquals(this.tokenStore.findTokenByValue(TOKEN_VALUES[14]).orElseThrow().getValue(), this.tokenStore.findTokenByValue(TOKEN_VALUES[13]).orElseThrow().getNext().orElseThrow().getValue());
        Assertions.assertEquals(this.tokenStore.findTokenByValue(TOKEN_VALUES[15]).orElseThrow().getValue(), this.tokenStore.findTokenByValue(TOKEN_VALUES[14]).orElseThrow().getNext().orElseThrow().getValue());
        Assertions.assertEquals(this.tokenStore.findTokenByValue(TOKEN_VALUES[18]).orElseThrow().getValue(), this.tokenStore.findTokenByValue(TOKEN_VALUES[15]).orElseThrow().getNext().orElseThrow().getValue());
        Assertions.assertEquals(this.tokenStore.findTokenByValue(TOKEN_VALUES[16]).orElseThrow().getValue(), this.tokenStore.findTokenByValue(TOKEN_VALUES[18]).orElseThrow().getNext().orElseThrow().getValue());
        Assertions.assertEquals(this.tokenStore.findTokenByValue(TOKEN_VALUES[17]).orElseThrow().getValue(), this.tokenStore.findTokenByValue(TOKEN_VALUES[16]).orElseThrow().getNext().orElseThrow().getValue());
    }

    /**
     *
     */
    @Test
    public void revokeToken() {
        final String tokenToRevoke = UUID.randomUUID().toString();
        this.tokenStore.save(tokenToRevoke);
        this.tokenStore.revoke(tokenToRevoke);
        this.tokenStore.findTokenByValue(tokenToRevoke).ifPresent(iToken -> Assertions.assertTrue(iToken::isRevoked));
    }

    /**
     *
     */
    @Test
    public void revokeLikedTokens() {
        this.tokenStore.save(TOKEN_VALUES[1]);
        this.tokenStore.save(TOKEN_VALUES[1], TOKEN_VALUES[2]);
        this.tokenStore.save(TOKEN_VALUES[2], TOKEN_VALUES[3]);
        this.tokenStore.save(TOKEN_VALUES[3], TOKEN_VALUES[4]);
        this.tokenStore.save(TOKEN_VALUES[4], TOKEN_VALUES[5]);

        this.tokenStore.revoke(TOKEN_VALUES[3]);

        this.tokenStore.findTokenByValue(TOKEN_VALUES[1]).ifPresent(iToken -> Assertions.assertTrue(iToken::isRevoked));
        this.tokenStore.findTokenByValue(TOKEN_VALUES[2]).ifPresent(iToken -> Assertions.assertTrue(iToken::isRevoked));
        this.tokenStore.findTokenByValue(TOKEN_VALUES[3]).ifPresent(iToken -> Assertions.assertTrue(iToken::isRevoked));
        this.tokenStore.findTokenByValue(TOKEN_VALUES[4]).ifPresent(iToken -> Assertions.assertTrue(iToken::isRevoked));
        this.tokenStore.findTokenByValue(TOKEN_VALUES[5]).ifPresent(iToken -> Assertions.assertTrue(iToken::isRevoked));

    }

    /**
     *
     */
    @Test
    public void revokeLikedTokensAndMustNotInterferirInOtherLinkedTokens() {
        this.tokenStore.save(TOKEN_VALUES[1]);
        this.tokenStore.save(TOKEN_VALUES[1], TOKEN_VALUES[2]);
        this.tokenStore.save(TOKEN_VALUES[2], TOKEN_VALUES[3]);
        this.tokenStore.save(TOKEN_VALUES[3], TOKEN_VALUES[4]);
        this.tokenStore.save(TOKEN_VALUES[4], TOKEN_VALUES[5]);

        this.tokenStore.save(TOKEN_VALUES[11]);
        this.tokenStore.save(TOKEN_VALUES[11], TOKEN_VALUES[12]);
        this.tokenStore.save(TOKEN_VALUES[12], TOKEN_VALUES[13]);
        this.tokenStore.save(TOKEN_VALUES[13], TOKEN_VALUES[14]);
        this.tokenStore.save(TOKEN_VALUES[14], TOKEN_VALUES[15]);
        this.tokenStore.save(TOKEN_VALUES[14], TOKEN_VALUES[18]);
        this.tokenStore.save(TOKEN_VALUES[12], TOKEN_VALUES[16]);
        this.tokenStore.save(TOKEN_VALUES[13], TOKEN_VALUES[17]);

        this.tokenStore.revoke(TOKEN_VALUES[3]);

        this.tokenStore.findTokenByValue(TOKEN_VALUES[1]).ifPresent(iToken -> Assertions.assertTrue(iToken::isRevoked));
        this.tokenStore.findTokenByValue(TOKEN_VALUES[2]).ifPresent(iToken -> Assertions.assertTrue(iToken::isRevoked));
        this.tokenStore.findTokenByValue(TOKEN_VALUES[3]).ifPresent(iToken -> Assertions.assertTrue(iToken::isRevoked));
        this.tokenStore.findTokenByValue(TOKEN_VALUES[4]).ifPresent(iToken -> Assertions.assertTrue(iToken::isRevoked));
        this.tokenStore.findTokenByValue(TOKEN_VALUES[5]).ifPresent(iToken -> Assertions.assertTrue(iToken::isRevoked));

        this.tokenStore.findTokenByValue(TOKEN_VALUES[11]).ifPresent(iToken -> Assertions.assertFalse(iToken::isRevoked));
        this.tokenStore.findTokenByValue(TOKEN_VALUES[12]).ifPresent(iToken -> Assertions.assertFalse(iToken::isRevoked));
        this.tokenStore.findTokenByValue(TOKEN_VALUES[13]).ifPresent(iToken -> Assertions.assertFalse(iToken::isRevoked));
        this.tokenStore.findTokenByValue(TOKEN_VALUES[14]).ifPresent(iToken -> Assertions.assertFalse(iToken::isRevoked));
        this.tokenStore.findTokenByValue(TOKEN_VALUES[15]).ifPresent(iToken -> Assertions.assertFalse(iToken::isRevoked));
        this.tokenStore.findTokenByValue(TOKEN_VALUES[16]).ifPresent(iToken -> Assertions.assertFalse(iToken::isRevoked));
        this.tokenStore.findTokenByValue(TOKEN_VALUES[17]).ifPresent(iToken -> Assertions.assertFalse(iToken::isRevoked));
        this.tokenStore.findTokenByValue(TOKEN_VALUES[18]).ifPresent(iToken -> Assertions.assertFalse(iToken::isRevoked));

    }

    /**
     *
     */
    @Test
    public void revokeTokenNotFoundedMustFail() {
        Assertions.assertThrows(java.lang.RuntimeException.class, () -> this.tokenStore.revoke("not inserted"));
    }

    /**
     *
     */
    @Test
    public void createSeveralTokens() {

        final int SIZE = 10;

        final String[] severalTokens = new String[SIZE];
        String root = null;
        String leaf = null;
        for (int i = 0; i < SIZE; i++) {
            final String token = UUID.randomUUID().toString();
            if (i == 0)
                root = token;
            else if (i == SIZE - 1)
                leaf = token;
            severalTokens[i] = token;
        }

        final Optional<IToken> rootToken = this.tokenStore.save(severalTokens);

        Assertions.assertEquals(root, rootToken.orElseThrow().getValue());
        Assertions.assertEquals(leaf, rootToken.orElseThrow().getLeaf().orElseThrow().getValue());

        Assertions.assertEquals(SIZE, rootToken.orElseThrow().count());

        rootToken.orElseThrow().printFromRoot();

    }

}


