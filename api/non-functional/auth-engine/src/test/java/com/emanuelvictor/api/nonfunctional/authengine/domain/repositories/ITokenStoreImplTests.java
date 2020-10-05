package com.emanuelvictor.api.nonfunctional.authengine.domain.repositories;

import com.emanuelvictor.api.nonfunctional.authengine.domain.entities.token.IToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class ITokenStoreImplTests extends ITokenStoreImplTestsAbstract {


    /**
     *
     */
    @Test
    void findTokenByValueMustNotFound() {
        final String tokenValue = UUID.randomUUID().toString();
        Assertions.assertTrue(this.tokenStore.findTokenByValue(tokenValue).isEmpty());
    }


    /**
     *
     */
    @Test
    void findTokenByValueMustFound() {
        final String tokenValueToFind = UUID.randomUUID().toString();
        final String tokenValueToCreate = UUID.randomUUID().toString();
        this.tokenStore.create(tokenValueToFind, tokenValueToCreate);
        Assertions.assertTrue(this.tokenStore.findTokenByValue(tokenValueToCreate).isPresent());
    }

    /**
     *
     */
    @Test
    void createToken() {
        final String tokenValueToFind = UUID.randomUUID().toString();
        final String tokenValueToCreate = UUID.randomUUID().toString();
        Assertions.assertTrue(this.tokenStore.findTokenByValue(tokenValueToCreate).isEmpty());
        this.tokenStore.create(tokenValueToFind, tokenValueToCreate);
        Assertions.assertTrue(this.tokenStore.findTokenByValue(tokenValueToCreate).isPresent());
    }

    /**
     *
     */
    @Test
    void createRepeatedTokenInThisSessionMustFail() {
        this.tokenStore.create("token1");
        this.tokenStore.create("token1", "token2");
        this.tokenStore.create("token2", "token3");
        this.tokenStore.create("token3", "token4");
        this.tokenStore.create("token4", "token5");

        Assertions.assertThrows(java.lang.RuntimeException.class, () -> this.tokenStore.create("token2", "token3"));

    }

    /**
     *
     */
    @Test
    void createRepeatedTokenLikeOtherSessionMustFail() {

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
    void createRepeatedTokenInnerOtherSessionMustFail() {

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

        Assertions.assertThrows(java.lang.RuntimeException.class, () -> this.tokenStore.create("token15", "token3"));

    }

    /**
     *
     */
    @Test
    void createTokenWithAFakePreviousMustFail() {
        Assertions.assertThrows(java.lang.RuntimeException.class, () -> this.tokenStore.create("token15", "token13"));
    }

    /**
     *
     */
    @Test
    void creatingLinkedTokensMustPass() {
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
    void revokeToken() {
    }
}


