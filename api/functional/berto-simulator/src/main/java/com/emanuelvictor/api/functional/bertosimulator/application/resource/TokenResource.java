package com.emanuelvictor.api.functional.bertosimulator.application.resource;

import com.emanuelvictor.api.functional.bertosimulator.domain.services.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    /*Dedicated to tests of the scope of the application. Client Credentials tests*/

    /**
     * @return ResponseEntity<String>
     */
    @GetMapping("must-return-403")
    @ResponseStatus(HttpStatus.OK)
//    @PreAuthorize("#oauth2.hasScope('root.access-manager.sessions.get')")
    public ResponseEntity<String> mustReturn403() {
        return tokenService.mustReturn403();
    }

    /**
     * To test of the access
     *
     * @return StringBuffer
     */
    @GetMapping("must-return-200")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> mustReturn200() {
        return tokenService.mustReturn200();
    }

}
