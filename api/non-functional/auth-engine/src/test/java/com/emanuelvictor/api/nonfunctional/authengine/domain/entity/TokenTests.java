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
        final IToken token1 = new Token(TOKEN_VALUES[1]);
        final IToken token2 = new Token(TOKEN_VALUES[2]);
        token1.add(token2);

        final IToken token3 = new Token(TOKEN_VALUES[3]);
        token1.add(token3);

        final IToken token4 = new Token(TOKEN_VALUES[4]);
        token3.add(token4);
        final IToken token5 = new Token(TOKEN_VALUES[6]);
        token4.add(token5);
        final IToken token7 = new Token(TOKEN_VALUES[7]);
        token4.add(token7);


        final IToken token6 = new Token(TOKEN_VALUES[5]);
        token3.add(token6);
        final IToken token8 = new Token(TOKEN_VALUES[8]);
        token6.add(token8);
        final IToken token9 = new Token(TOKEN_VALUES[9]);
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
        final IToken token1 = new Token(TOKEN_VALUES[1]);

        final IToken token2 = new Token(TOKEN_VALUES[2]);
        token1.add(token2);

        final IToken token3 = new Token(TOKEN_VALUES[3]);
        token1.add(token3);

        final IToken token4 = new Token(TOKEN_VALUES[4]);
        token2.add(token4);

        final IToken token5 = new Token(TOKEN_VALUES[5]);
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
        final IToken token1 = new Token(TOKEN_VALUES[1]);

        final IToken token2 = new Token(TOKEN_VALUES[2]);
        token1.add(token2);

        final IToken token3 = new Token(TOKEN_VALUES[3]);
        token1.add(token3);

        final IToken token4 = new Token(TOKEN_VALUES[4]);
        token2.add(token4);

        final IToken token5 = new Token(TOKEN_VALUES[5]);
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

        Assert.notNull(dataSet().stream().findFirst().orElseThrow().findByValue(TOKEN_VALUES[2]));

        Assert.notNull(dataSet().stream().findFirst().orElseThrow().findByValue(TOKEN_VALUES[3]));

    }

    /**
     *
     */
    @Test
    public void getRoot() {
        Assertions.assertEquals(dataSet().stream().findFirst().orElseThrow().findByValue(TOKEN_VALUES[4]).orElseThrow().getRoot().orElseThrow().getValue(), TOKEN_VALUES[1]);
    }

    /**
     *
     */
    @Test
    public void getLeaf() {
        Assertions.assertEquals(dataSet().stream().findFirst().orElseThrow().findByValue(TOKEN_VALUES[4]).orElseThrow().getLeaf().orElseThrow().getValue(), TOKEN_VALUES[9]);
    }

    /**
     *
     */
    @Test
    public void getAccessToken() {
        final IToken iToken = dataSet().stream().findFirst().orElseThrow().findByValue(TOKEN_VALUES[4]).orElseThrow();
        Assertions.assertEquals(iToken.getAccess().orElseThrow().getValue(), TOKEN_VALUES[iToken.count() - 1]);
    }

    /**
     *
     */
    @Test
    public void getRefreshToken() {
        final IToken iToken = dataSet().stream().findFirst().orElseThrow().findByValue(TOKEN_VALUES[4]).orElseThrow();
        Assertions.assertEquals(iToken.getRefresh().orElseThrow().getValue(), TOKEN_VALUES[iToken.count()]);
    }

    /**
     *
     */
    public static final String[] TOKEN_VALUES = new String[]{"token0", "token1", "token2", "token3", "token4", "token5", "token6", "token7", "token8", "token9", "token10", "token11", "token12", "token13", "token14", "token15", "token16", "token17", "token18", "token19"};

    /**
     * @return Set<IToken>
     */
    public static Set<IToken> dataSet() {

        //Creates two composites containing the tokens
        final IToken token1 = new Token(TOKEN_VALUES[1]);
        final IToken token2 = new Token(TOKEN_VALUES[2]);
        token1.add(token2);

        final IToken token3 = new Token(TOKEN_VALUES[3]);
        token1.add(token3);

        final IToken token4 = new Token(TOKEN_VALUES[4]);
        token3.add(token4);
        final IToken token5 = new Token(TOKEN_VALUES[6]);
        token4.add(token5);
        final IToken token7 = new Token(TOKEN_VALUES[7]);
        token4.add(token7);


        final IToken token6 = new Token(TOKEN_VALUES[5]);
        token3.add(token6);
        final IToken token8 = new Token(TOKEN_VALUES[8]);
        token6.add(token8);
        final IToken token9 = new Token(TOKEN_VALUES[9]);
        token6.add(token9);

        return Set.of(token1);

    }
}
