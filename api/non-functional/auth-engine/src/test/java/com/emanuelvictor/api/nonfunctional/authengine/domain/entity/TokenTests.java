package com.emanuelvictor.api.nonfunctional.authengine.domain.entity;

import com.emanuelvictor.api.nonfunctional.authengine.infrastructure.token.IToken;
import com.emanuelvictor.api.nonfunctional.authengine.domain.entities.Token;
import com.emanuelvictor.api.nonfunctional.authengine.domain.AbstractsUnitTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.Set;

public class TokenTests extends AbstractsUnitTests {


    /**
     *
     */
    @Test
    public void recursiveTokensHanlder() {

        //Creates two composites containing the tokens
        final IToken token1 = new Token("token1");
        final IToken token2 = new Token("token2");
        token1.add(token2);

        final IToken token3 = new Token("token3");
        token1.add(token3);

        final IToken token4 = new Token("token4");
        token3.add(token4);
        final IToken token5 = new Token("token6");
        token4.add(token5);
        final IToken token7 = new Token("token7");
        token4.add(token7);


        final IToken token6 = new Token("token5");
        token3.add(token6);
        final IToken token8 = new Token("token8");
        token6.add(token8);
        final IToken token9 = new Token("token9");
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

        token5.printFromRoot();

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
    public void addTokenMustPass() {
        final IToken token1 = new Token("token1");

        final IToken token2 = new Token("token2");
        token1.add(token2);

        final IToken token3 = new Token("token3");
        token1.add(token3);

        final IToken token4 = new Token("token4");
        token2.add(token4);

        final IToken token5 = new Token("token5");
        token1.add(token5);

        Assert.notNull(token4.getNext(), "The next token must be non null");
        Assert.isTrue(token4.getNext().orElseThrow().equals(token5), "The next token must be equals to token5");

        Assert.notNull(token2.getNext(), "The next token must be non null");
        Assert.isTrue(token2.getNext().orElseThrow().equals(token3), "The next token must be equals to token3");
        Assert.notNull(token2.getNext().orElseThrow().getNext().orElseThrow().getNext(), "The next token must be non null");
        Assert.isTrue(token2.getNext().orElseThrow().getNext().orElseThrow().getNext().orElseThrow().equals(token5), "The next token must be equals to token5");
    }

    /**
     *
     */
    @Test
    public void countTests() {
        final IToken token1 = new Token("token1");

        final IToken token2 = new Token("token2");
        token1.add(token2);

        final IToken token3 = new Token("token3");
        token1.add(token3);

        final IToken token4 = new Token("token4");
        token2.add(token4);

        final IToken token5 = new Token("token5");
        token1.add(token5);

        final int expected = 5;

        Assertions.assertEquals(expected, token1.count());

        Assertions.assertEquals(expected, token4.count());

        Assertions.assertEquals(expected, token3.count());

        Assertions.assertEquals(expected, token5.count());

    }

    /**
     *
     */
    @Test
    public void findByValue() {

        Assert.notNull(dataSet().stream().findFirst().orElseThrow().findByValue("token2"));

        Assert.notNull(dataSet().stream().findFirst().orElseThrow().findByValue("token3"));

    }

    /**
     * @return Set<IToken>
     */
    public static Set<IToken> dataSet() {

        //Creates two composites containing the tokens
        final IToken token1 = new Token("token1");
        final IToken token2 = new Token("token2");
        token1.add(token2);

        final IToken token3 = new Token("token3");
        token1.add(token3);

        final IToken token4 = new Token("token4");
        token3.add(token4);
        final IToken token5 = new Token("token6");
        token4.add(token5);
        final IToken token7 = new Token("token7");
        token4.add(token7);


        final IToken token6 = new Token("token5");
        token3.add(token6);
        final IToken token8 = new Token("token8");
        token6.add(token8);
        final IToken token9 = new Token("token9");
        token6.add(token9);

        return Set.of(token1);

    }
}
