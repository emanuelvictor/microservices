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
     * todo deve ter preauthorize
     */
    @DeleteMapping("{token}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable final String token) {
        tokenService.revoke(token);
    }

    /**
     * todo deve ter preauthorize
     */
    @GetMapping("{name}")
    @ResponseStatus(HttpStatus.OK)
    public Set<Object> findTokenByName(@PathVariable final String name) {
        return tokenService.findTokenByName(name);
    }

}
