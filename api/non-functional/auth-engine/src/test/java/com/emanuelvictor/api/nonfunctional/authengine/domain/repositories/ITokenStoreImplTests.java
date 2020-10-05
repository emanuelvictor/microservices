package com.emanuelvictor.api.nonfunctional.authengine.domain.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.UUID;

public class ITokenStoreImplTests  extends ITokenStoreImplTestsAbstract {


    /**
     *
     */
    @Test
    void findTokenByValueMustNotFound() {
        final String tokenValue = UUID.randomUUID().toString();
        Assert.isTrue(this.tokenStore.findTokenByValue(tokenValue).isEmpty());
    }


    /**
     *
     */
    @Test
    void findTokenByValueMustFound() {
        final String tokenValueToFind = UUID.randomUUID().toString();
        final String tokenValueToCreate = UUID.randomUUID().toString();
        this.tokenStore.create(tokenValueToFind, tokenValueToCreate);
        Assert.isTrue(this.tokenStore.findTokenByValue(tokenValueToCreate).isPresent());
    }

    /**
     *
     */
    @Test
    void createToken() {
        final String tokenValueToFind = UUID.randomUUID().toString();
        final String tokenValueToCreate = UUID.randomUUID().toString();
        Assert.isTrue(this.tokenStore.findTokenByValue(tokenValueToCreate).isEmpty());
        this.tokenStore.create(tokenValueToFind, tokenValueToCreate);
        Assert.isTrue(this.tokenStore.findTokenByValue(tokenValueToCreate).isPresent());
    }

    /**
     *
     */
    @Test
    void revokeToken() {
    }

    /**
     *
     */
    @Test
    void createTokenAndKeepOthersActivated() {
    }
}
