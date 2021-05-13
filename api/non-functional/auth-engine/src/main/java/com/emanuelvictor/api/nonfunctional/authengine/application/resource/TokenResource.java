package com.emanuelvictor.api.nonfunctional.authengine.application.resource;

import com.emanuelvictor.api.nonfunctional.authengine.domain.services.TokenService;
import com.emanuelvictor.api.nonfunctional.authengine.infrastructure.token.domain.entities.IToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.DefaultSessionAttributeStore;

import java.util.Set;

/**
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("tokens")
public class TokenResource {

    /**
     *
     */
    private final TokenService tokenService;

    /**
     * Todo falha de segurança, deve ter preauthorize
     */
    @DeleteMapping("{token}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable final String token) {
        tokenService.revokeToken(token);
    }

    /**
     * Todo falha de segurança, deve ter preauthorize
     *
     */
    @GetMapping("{name}")
    @ResponseStatus(HttpStatus.OK)
    public Set<IToken> findTokenByName(@PathVariable final String name) {
        return tokenService.listTokensByName(name);
    }

}
