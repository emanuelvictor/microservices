package com.emanuelvictor.api.nonfunctional.authengine.domain.services;

import com.emanuelvictor.api.nonfunctional.authengine.domain.AbstractsTests;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.Assert;

import java.util.Set;


//@SpringBootTest
public class TokenServiceTests extends AbstractsTests {

    /**
     *
     */
    @Test
    public void extractClientsId() {

        final Set<SimpleGrantedAuthority> authorities = Set.of(
                new SimpleGrantedAuthority("root"),
                new SimpleGrantedAuthority("root/account-manager/access-groups/put/activate"),
                new SimpleGrantedAuthority("root/account-manager"),
                new SimpleGrantedAuthority("root/financial"),
                new SimpleGrantedAuthority("root/financial/insert-coin"),
                new SimpleGrantedAuthority("root/falcatrua"),
                new SimpleGrantedAuthority("root/falcatrua/outra")
        );

        Assert.isTrue("account-manager;financial;falcatrua".equals(String.join(";", TokenService.extractClientsId(authorities))), "");

    }

}
