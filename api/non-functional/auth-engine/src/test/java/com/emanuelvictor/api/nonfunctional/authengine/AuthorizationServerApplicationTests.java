package com.emanuelvictor.api.nonfunctional.authengine;

import com.emanuelvictor.api.nonfunctional.authengine.domain.entities.token.IToken;
import com.emanuelvictor.api.nonfunctional.authengine.domain.entities.token.Token;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.Assert;

import java.util.Optional;
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
        final Optional<IToken> token1 = Optional.of(new Token("token1"));
        final Optional<IToken> token2 = Optional.of(new Token("token2"));
        token1.orElseThrow().add(token2);

        final Optional<IToken> token3 = Optional.of(new Token("token3"));
        token1.orElseThrow().add(token3);

        final Optional<IToken> token4 = Optional.of(new Token("token4"));
        token3.orElseThrow().add(token4);
        final Optional<IToken> token5 = Optional.of(new Token("token6"));
        token4.orElseThrow().add(token5);
        final Optional<IToken> token7 = Optional.of(new Token("token7"));
        token4.orElseThrow().add(token7);


        final Optional<IToken> token6 = Optional.of(new Token("token5"));
        token3.orElseThrow().add(token6);
        final Optional<IToken> token8 = Optional.of(new Token("token8"));
        token6.orElseThrow().add(token8);
        final Optional<IToken> token9 = Optional.of(new Token("token9"));
        token6.orElseThrow().add(token9);

        Assert.isTrue(!token1.orElseThrow().isRevoked());
        Assert.isTrue(!token2.orElseThrow().isRevoked());
        Assert.isTrue(!token3.orElseThrow().isRevoked());
        Assert.isTrue(!token4.orElseThrow().isRevoked());
        Assert.isTrue(!token5.orElseThrow().isRevoked());
        Assert.isTrue(!token7.orElseThrow().isRevoked());
        Assert.isTrue(!token6.orElseThrow().isRevoked());
        Assert.isTrue(!token8.orElseThrow().isRevoked());
        Assert.isTrue(!token9.orElseThrow().isRevoked());

        token5.orElseThrow().printPrevious();

        token5.orElseThrow().revoke();

        Assert.isTrue(token1.orElseThrow().isRevoked());
        Assert.isTrue(token2.orElseThrow().isRevoked());
        Assert.isTrue(token3.orElseThrow().isRevoked());
        Assert.isTrue(token4.orElseThrow().isRevoked());
        Assert.isTrue(token6.orElseThrow().isRevoked());
        Assert.isTrue(token7.orElseThrow().isRevoked());
        Assert.isTrue(token5.orElseThrow().isRevoked());
        Assert.isTrue(token8.orElseThrow().isRevoked());
        Assert.isTrue(token9.orElseThrow().isRevoked());

    }

    /**
     *
     */
    @Test
    void addTokenMustPass() {
        final Optional<IToken> token1 = Optional.of(new Token("token1"));

        final Optional<IToken> token2 = Optional.of(new Token("token2"));
        token1.orElseThrow().add(token2);

        final Optional<IToken> token3 = Optional.of(new Token("token3"));
        token1.orElseThrow().add(token3);

        final Optional<IToken> token4 = Optional.of(new Token("token4"));
        token2.orElseThrow().add(token4);

        final Optional<IToken> token5 = Optional.of(new Token("token5"));
        token1.orElseThrow().add(token5);

        Assert.notNull(token4.orElseThrow().getNext(), "The next token must be non null");
        Assert.isTrue(token4.orElseThrow().getNext().equals(token5), "The next token must be equals to token5");

        Assert.notNull(token2.orElseThrow().getNext(), "The next token must be non null");
        Assert.isTrue(token2.orElseThrow().getNext().equals(token3), "The next token must be equals to token3");
        Assert.notNull(token2.orElseThrow().getNext().orElseThrow().getNext().orElseThrow().getNext(), "The next token must be non null");
        Assert.isTrue(token2.orElseThrow().getNext().orElseThrow().getNext().orElseThrow().getNext().equals(token5), "The next token must be equals to token5");
    }

    /**
     *
     */
    @Test
    void findByValue() {

        Assert.notNull(dataSet().stream().findFirst().orElseThrow().orElseThrow().findByValue("token2"));

        Assert.notNull(dataSet().stream().findFirst().orElseThrow().orElseThrow().findByValue("token3"));

    }


    public static Set<Optional<IToken>> dataSet() {

        //Creates two composites containing the tokens
        final Optional<IToken> token1 = Optional.of(new Token("token1"));
        final Optional<IToken> token2 = Optional.of(new Token("token2"));
        token1.orElseThrow().add(token2);

        final Optional<IToken> token3 = Optional.of(new Token("token3"));
        token1.orElseThrow().add(token3);

        final Optional<IToken> token4 = Optional.of(new Token("token4"));
        token3.orElseThrow().add(token4);
        final Optional<IToken> token5 = Optional.of(new Token("token6"));
        token4.orElseThrow().add(token5);
        final Optional<IToken> token7 = Optional.of(new Token("token7"));
        token4.orElseThrow().add(token7);


        final Optional<IToken> token6 = Optional.of(new Token("token5"));
        token3.orElseThrow().add(token6);
        final Optional<IToken> token8 = Optional.of(new Token("token8"));
        token6.orElseThrow().add(token8);
        final Optional<IToken> token9 = Optional.of(new Token("token9"));
        token6.orElseThrow().add(token9);

        return Set.of(token1);

    }

}
