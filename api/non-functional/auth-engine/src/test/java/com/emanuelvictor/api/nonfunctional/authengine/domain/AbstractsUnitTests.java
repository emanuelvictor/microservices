package com.emanuelvictor.api.nonfunctional.authengine.domain;


import com.emanuelvictor.api.nonfunctional.authengine.domain.repositories.ITokenStore;
import com.emanuelvictor.api.nonfunctional.authengine.domain.repositories.impl.ITokenStoreImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
public abstract class AbstractsUnitTests {

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
