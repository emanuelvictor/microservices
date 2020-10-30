package com.emanuelvictor.api.nonfunctional.authengine.application.resource;

import com.emanuelvictor.api.nonfunctional.authengine.domain.services.TokenService;
import com.emanuelvictor.api.nonfunctional.authengine.infrastructure.token.domain.entities.IToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
     *
     */
    @GetMapping("{name}")
    @ResponseStatus(HttpStatus.OK)
    public Set<IToken> findTokenByName(@PathVariable final String name) {
        return tokenService.listTokensByName(name);
    }

}
