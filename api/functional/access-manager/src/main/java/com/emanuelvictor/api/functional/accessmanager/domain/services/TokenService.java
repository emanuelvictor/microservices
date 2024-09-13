package com.emanuelvictor.api.functional.accessmanager.domain.services;

import com.emanuelvictor.api.functional.accessmanager.application.spring.oauth.custom.JwtTokenStore; // TODO acoplamento
import com.emanuelvictor.api.functional.accessmanager.domain.repositories.feign.ITokenFeignRepository;
import lombok.RequiredArgsConstructor; // TODO acoplamento
import org.springframework.http.ResponseEntity; // TODO acoplamento
import org.springframework.security.oauth2.provider.token.TokenStore; // TODO acoplamento
import org.springframework.stereotype.Service;

import java.util.Set;


/**
 * TODO parece ser um application service
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 2.0.0, 04/01/2020
 */
@Service
@RequiredArgsConstructor
public class TokenService {

    /**
     *
     */
    private final TokenStore tokenStore;

    /**
     *
     */
    private final ITokenFeignRepository tokenFeignRepository;

    /**
     * @param token String
     */
    public void revoke(final String token) {
        // Black list
        ((JwtTokenStore) this.tokenStore).revoke(token);
    }

    /**
     * @param name String
     * @return Set<String>
     */
    public Set<Object> findTokenByName(final String name) {
        return tokenFeignRepository.findTokenByName(name);
    }

    /*Dedicated to tests of the scope of the application. Client Credentials tests*/

    /**
     *
     * @return ResponseEntity<String>
     */
    public ResponseEntity<String> mustReturn403() {
        return tokenFeignRepository.mustReturn403();
    }

    /**
     * @return ResponseEntity<String>
     */
    public ResponseEntity<String> mustReturn200() {
        return tokenFeignRepository.mustReturn200();
    }
}
