package com.emanuelvictor.api.nonfunctional.authengine;

import com.emanuelvictor.api.nonfunctional.authengine.domain.entities.token.RefreshToken;
import org.junit.jupiter.api.Test;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.Assert;

import java.util.Set;

import static com.emanuelvictor.api.nonfunctional.authengine.domain.services.ServiceToken.extractClientsId;


//@SpringBootTest
class AuthorizationServerApplicationTests {

    @Test
    void contextLoads() {

        final Set<SimpleGrantedAuthority> authorities = Set.of(
                new SimpleGrantedAuthority("root"),
                new SimpleGrantedAuthority("root/account-manager/access-groups/put/activate"),
                new SimpleGrantedAuthority("root/account-manager"),
                new SimpleGrantedAuthority("root/financial"),
                new SimpleGrantedAuthority("root/financial/insert-coin"),
                new SimpleGrantedAuthority("root/falcatrua"),
                new SimpleGrantedAuthority("root/falcatrua/outra")
        );

        Assert.isTrue("account-manager;financial;falcatrua".equals(String.join(";", extractClientsId(authorities))), "");

    }

    /**
     *
     */
    @Test
    void recursiveTokensHanlder() {

        //Creates two composites containing the tokens
        final RefreshToken token1 = new RefreshToken("token1");
        final RefreshToken token2 = new RefreshToken("token2");
        token1.add(token2);

        final RefreshToken token3 = new RefreshToken("token3");
        token1.add(token3);

        final RefreshToken token4 = new RefreshToken("token4");
        token3.add(token4);
        final RefreshToken token5 = new RefreshToken("token6");
        token4.add(token5);
        final RefreshToken token7 = new RefreshToken("token7");
        token4.add(token7);


        final RefreshToken token6 = new RefreshToken("token5");
        token3.add(token6);
        final RefreshToken token8 = new RefreshToken("token8");
        token6.add(token8);
        final RefreshToken token9 = new RefreshToken("token9");
        token6.add(token9);

        Assert.isTrue(!token1.isRevoked());
        Assert.isTrue(!token2.isRevoked());
        Assert.isTrue(!token3.isRevoked());
        Assert.isTrue(!token4.isRevoked());
        Assert.isTrue(!token5.isRevoked());
        Assert.isTrue(!token7.isRevoked());
        Assert.isTrue(!token6.isRevoked());
        Assert.isTrue(!token8.isRevoked());
        Assert.isTrue(!token9.isRevoked());

        token5.printPrevious();

        token5.revoke();

        Assert.isTrue(token1.isRevoked());
        Assert.isTrue(token2.isRevoked());
        Assert.isTrue(token3.isRevoked());
        Assert.isTrue(token4.isRevoked());
        Assert.isTrue(token6.isRevoked());
        Assert.isTrue(token7.isRevoked());
        Assert.isTrue(token5.isRevoked());
        Assert.isTrue(token8.isRevoked());
        Assert.isTrue(token9.isRevoked());

    }

    /**
     *
     */
    @Test
    void addTokenMustPass(){
        final RefreshToken token1 = new RefreshToken("token1");

        final RefreshToken token2 = new RefreshToken("token2");
        token1.add(token2);

        final RefreshToken token3 = new RefreshToken("token3");
        token1.add(token3);

        final RefreshToken token4 = new RefreshToken("token4");
        token2.add(token4);

        final RefreshToken token5 = new RefreshToken("token5");
        token1.add(token5);

        Assert.notNull(token4.getNext(), "The next token must be non null");
        Assert.isTrue(token4.getNext().equals(token5), "The next token must be equals to token5");

        Assert.notNull(token2.getNext(), "The next token must be non null");
        Assert.isTrue(token2.getNext().equals(token3), "The next token must be equals to token3");
        Assert.notNull(token2.getNext().getNext().getNext(), "The next token must be non null");
        Assert.isTrue(token2.getNext().getNext().getNext().equals(token5), "The next token must be equals to token5");
    }

}
