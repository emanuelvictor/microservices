package com.emanuelvictor.api.nonfunctional.authengine.domain.repositories;


import com.emanuelvictor.api.nonfunctional.authengine.domain.repositories.impl.ITokenStoreImpl;
import org.junit.jupiter.api.BeforeEach;

public class ITokenStoreImplTestsAbstract {

    /**
     *
     */
    protected ITokenStore tokenStore;

    /**
     *
     */
//    @Before before to junit
    @BeforeEach
    public void beforeTests() {
        tokenStore = new ITokenStoreImpl();
    }
}
