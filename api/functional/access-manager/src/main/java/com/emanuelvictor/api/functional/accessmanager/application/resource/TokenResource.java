package com.emanuelvictor.api.functional.accessmanager.application.resource;

import com.emanuelvictor.api.functional.accessmanager.domain.services.TokenService;
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
    @DeleteMapping("/revoke/{token}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable final String token) {
        tokenService.revoke(token);
    }

    /**
     *
     */
    @GetMapping("{name}")
    @ResponseStatus(HttpStatus.OK)
    public Set<Object> findTokenByName(@PathVariable final String name) {
        return tokenService.findTokenByName(name);
    }

}
