package com.emanuelvictor.api.nonfunctional.authengine.domain.repositories;

import com.emanuelvictor.api.nonfunctional.authengine.domain.AbstractsUnitTests;
import com.emanuelvictor.api.nonfunctional.authengine.domain.entities.token.IToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class ITokenStoreImplTests extends AbstractsUnitTests {


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
        this.tokenStore.create(tokenValueToCreate);
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
        this.tokenStore.create(tokenValueToFind);
        Assertions.assertTrue(this.tokenStore.findTokenByValue(tokenValueToFind).isPresent());
        this.tokenStore.create(tokenValueToFind, tokenValueToCreate);
        Assertions.assertTrue(this.tokenStore.findTokenByValue(tokenValueToCreate).isPresent());
    }

    /**
     *
     */
    @Test
    public void countTests() {

        final IToken firstToken = this.tokenStore.create("token1").orElseThrow();
        this.tokenStore.create("token1", "token2");
        this.tokenStore.create("token2", "token3");
        this.tokenStore.create("token3", "token4");
        this.tokenStore.create("token4", "token5");

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

        final IToken firstToken = this.tokenStore.create("token1").orElseThrow();
        this.tokenStore.create("token1", "token2");
        this.tokenStore.create("token2", "token3");
        this.tokenStore.create("token3", "token4");
        this.tokenStore.create("token4", "token5");

        // Original size
        final int size = firstToken.count();

        // Create new token
        this.tokenStore.create("token2", "token3");

        // The tokens must be have 5 tokens
        Assertions.assertEquals(size, this.tokenStore.findTokenByValue("token4").orElseThrow().count());

    }

    /**
     *
     */
    @Test
    public void createRepeatedTokenLikeOtherSessionMustFail() {

        this.tokenStore.create("token1");
        this.tokenStore.create("token1", "token2");
        this.tokenStore.create("token2", "token3");
        this.tokenStore.create("token3", "token4");
        this.tokenStore.create("token4", "token5");

        Assertions.assertThrows(java.lang.RuntimeException.class, () -> this.tokenStore.create("token3"));

    }

    /**
     *
     */
    @Test
    public void createRepeatedTokenInnerOtherSessionBeUpdated() {

        this.tokenStore.create("token1");
        this.tokenStore.create("token1", "token2");
        this.tokenStore.create("token2", "token3");
        this.tokenStore.create("token3", "token4");
        this.tokenStore.create("token4", "token5");

        final int firstSizeExpected = this.tokenStore.findTokenByValue("token3").orElseThrow().count();

        this.tokenStore.create("token11");
        this.tokenStore.create("token11", "token12");
        this.tokenStore.create("token12", "token13");
        this.tokenStore.create("token13", "token14");
        this.tokenStore.create("token14", "token15");

        final int secondSizeExpected = this.tokenStore.findTokenByValue("token11").orElseThrow().count();

        // Create repeated token
        this.tokenStore.create("token15", "token3");

        Assertions.assertEquals(firstSizeExpected, this.tokenStore.findTokenByValue("token4").orElseThrow().count());

        Assertions.assertEquals(secondSizeExpected, this.tokenStore.findTokenByValue("token14").orElseThrow().count());

    }

    /**
     *
     */
    @Test
    public void createTokenWithAFakePreviousMustBeCreateTwoTokensInRoot() {
        this.tokenStore.create("token15", "token13");
        Assertions.assertEquals(2, this.tokenStore.findTokenByValue("token15").orElseThrow().count());

        final IToken token = this.tokenStore.findTokenByValue("token15").orElseThrow();
        Assertions.assertTrue(token.getPrevious().isEmpty());
        Assertions.assertTrue(token.getNext().isPresent());
        Assertions.assertEquals("token15", token.getValue());
        Assertions.assertEquals("token13", token.getNext().orElseThrow().getValue());
        Assertions.assertTrue(token.getNext().orElseThrow().getPrevious().isPresent());
        Assertions.assertTrue(token.getNext().orElseThrow().getNext().isEmpty());
    }

    /**
     *
     */
    @Test
    public void createTokenWithTokenToFindIsNullMustFail() {
        Assertions.assertThrows(java.lang.RuntimeException.class, () -> this.tokenStore.create(null, "token13"));
    }

    /**
     *
     */
    @Test
    public void creatingLinkedTokensMustPass() {
        this.tokenStore.create("token1");
        this.tokenStore.create("token1", "token2");
        this.tokenStore.create("token2", "token3");
        this.tokenStore.create("token3", "token4");
        this.tokenStore.create("token4", "token5");
        this.tokenStore.findTokenByValue("token3").ifPresent(IToken::printFromRoot);

        Assertions.assertEquals(this.tokenStore.findTokenByValue("token2").orElseThrow().getValue(), this.tokenStore.findTokenByValue("token1").orElseThrow().getNext().orElseThrow().getValue());
        Assertions.assertEquals(this.tokenStore.findTokenByValue("token3").orElseThrow().getValue(), this.tokenStore.findTokenByValue("token2").orElseThrow().getNext().orElseThrow().getValue());
        Assertions.assertEquals(this.tokenStore.findTokenByValue("token4").orElseThrow().getValue(), this.tokenStore.findTokenByValue("token3").orElseThrow().getNext().orElseThrow().getValue());
        Assertions.assertEquals(this.tokenStore.findTokenByValue("token5").orElseThrow().getValue(), this.tokenStore.findTokenByValue("token4").orElseThrow().getNext().orElseThrow().getValue());

        this.tokenStore.create("token11");
        this.tokenStore.create("token11", "token12");
        this.tokenStore.create("token12", "token13");
        this.tokenStore.create("token13", "token14");
        this.tokenStore.create("token14", "token15");
        this.tokenStore.create("token14", "token18");
        this.tokenStore.create("token12", "token16");
        this.tokenStore.create("token13", "token17");
        this.tokenStore.findTokenByValue("token13").ifPresent(IToken::printFromRoot);

        Assertions.assertEquals(this.tokenStore.findTokenByValue("token12").orElseThrow().getValue(), this.tokenStore.findTokenByValue("token11").orElseThrow().getNext().orElseThrow().getValue());
        Assertions.assertEquals(this.tokenStore.findTokenByValue("token13").orElseThrow().getValue(), this.tokenStore.findTokenByValue("token12").orElseThrow().getNext().orElseThrow().getValue());
        Assertions.assertEquals(this.tokenStore.findTokenByValue("token14").orElseThrow().getValue(), this.tokenStore.findTokenByValue("token13").orElseThrow().getNext().orElseThrow().getValue());
        Assertions.assertEquals(this.tokenStore.findTokenByValue("token15").orElseThrow().getValue(), this.tokenStore.findTokenByValue("token14").orElseThrow().getNext().orElseThrow().getValue());
        Assertions.assertEquals(this.tokenStore.findTokenByValue("token18").orElseThrow().getValue(), this.tokenStore.findTokenByValue("token15").orElseThrow().getNext().orElseThrow().getValue());
        Assertions.assertEquals(this.tokenStore.findTokenByValue("token16").orElseThrow().getValue(), this.tokenStore.findTokenByValue("token18").orElseThrow().getNext().orElseThrow().getValue());
        Assertions.assertEquals(this.tokenStore.findTokenByValue("token17").orElseThrow().getValue(), this.tokenStore.findTokenByValue("token16").orElseThrow().getNext().orElseThrow().getValue());
    }

    /**
     *
     */
    @Test
    public void revokeToken() {
        final String tokenToRevoke = UUID.randomUUID().toString();
        this.tokenStore.create(tokenToRevoke);
        this.tokenStore.revoke(tokenToRevoke);
        this.tokenStore.findTokenByValue(tokenToRevoke).ifPresent(iToken -> Assertions.assertTrue(iToken::isRevoked));
    }

    /**
     *
     */
    @Test
    public void revokeLikedTokens() {
        this.tokenStore.create("token1");
        this.tokenStore.create("token1", "token2");
        this.tokenStore.create("token2", "token3");
        this.tokenStore.create("token3", "token4");
        this.tokenStore.create("token4", "token5");

        this.tokenStore.revoke("token3");

        this.tokenStore.findTokenByValue("token1").ifPresent(iToken -> Assertions.assertTrue(iToken::isRevoked));
        this.tokenStore.findTokenByValue("token2").ifPresent(iToken -> Assertions.assertTrue(iToken::isRevoked));
        this.tokenStore.findTokenByValue("token3").ifPresent(iToken -> Assertions.assertTrue(iToken::isRevoked));
        this.tokenStore.findTokenByValue("token4").ifPresent(iToken -> Assertions.assertTrue(iToken::isRevoked));
        this.tokenStore.findTokenByValue("token5").ifPresent(iToken -> Assertions.assertTrue(iToken::isRevoked));

    }

    /**
     *
     */
    @Test
    public void revokeLikedTokensAndMustNotInterferirInOtherLinkedTokens() {
        this.tokenStore.create("token1");
        this.tokenStore.create("token1", "token2");
        this.tokenStore.create("token2", "token3");
        this.tokenStore.create("token3", "token4");
        this.tokenStore.create("token4", "token5");

        this.tokenStore.create("token11");
        this.tokenStore.create("token11", "token12");
        this.tokenStore.create("token12", "token13");
        this.tokenStore.create("token13", "token14");
        this.tokenStore.create("token14", "token15");
        this.tokenStore.create("token14", "token18");
        this.tokenStore.create("token12", "token16");
        this.tokenStore.create("token13", "token17");

        this.tokenStore.revoke("token3");

        this.tokenStore.findTokenByValue("token1").ifPresent(iToken -> Assertions.assertTrue(iToken::isRevoked));
        this.tokenStore.findTokenByValue("token2").ifPresent(iToken -> Assertions.assertTrue(iToken::isRevoked));
        this.tokenStore.findTokenByValue("token3").ifPresent(iToken -> Assertions.assertTrue(iToken::isRevoked));
        this.tokenStore.findTokenByValue("token4").ifPresent(iToken -> Assertions.assertTrue(iToken::isRevoked));
        this.tokenStore.findTokenByValue("token5").ifPresent(iToken -> Assertions.assertTrue(iToken::isRevoked));

        this.tokenStore.findTokenByValue("token11").ifPresent(iToken -> Assertions.assertFalse(iToken::isRevoked));
        this.tokenStore.findTokenByValue("token12").ifPresent(iToken -> Assertions.assertFalse(iToken::isRevoked));
        this.tokenStore.findTokenByValue("token13").ifPresent(iToken -> Assertions.assertFalse(iToken::isRevoked));
        this.tokenStore.findTokenByValue("token14").ifPresent(iToken -> Assertions.assertFalse(iToken::isRevoked));
        this.tokenStore.findTokenByValue("token15").ifPresent(iToken -> Assertions.assertFalse(iToken::isRevoked));
        this.tokenStore.findTokenByValue("token16").ifPresent(iToken -> Assertions.assertFalse(iToken::isRevoked));
        this.tokenStore.findTokenByValue("token17").ifPresent(iToken -> Assertions.assertFalse(iToken::isRevoked));
        this.tokenStore.findTokenByValue("token18").ifPresent(iToken -> Assertions.assertFalse(iToken::isRevoked));

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

        final Optional<IToken> rootToken = this.tokenStore.create(root, severalTokens);

        Assertions.assertEquals(root, rootToken.orElseThrow().getValue());
        Assertions.assertEquals(leaf, rootToken.orElseThrow().getLeaf().orElseThrow().getValue());

        Assertions.assertEquals(SIZE, rootToken.orElseThrow().count());

        rootToken.orElseThrow().printFromRoot();

    }

    /**
     *
     */
    @Test
    public void createSeveralTokensWithoutTokenToFind() {

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

        final Optional<IToken> rootToken = this.tokenStore.create(severalTokens);

        Assertions.assertEquals(root, rootToken.orElseThrow().getValue());
        Assertions.assertEquals(leaf, rootToken.orElseThrow().getLeaf().orElseThrow().getValue());

        Assertions.assertEquals(SIZE, rootToken.orElseThrow().count());

        rootToken.orElseThrow().printFromRoot();

    }
}


