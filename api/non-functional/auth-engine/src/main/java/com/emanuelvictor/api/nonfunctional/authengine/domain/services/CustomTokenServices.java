package com.emanuelvictor.api.nonfunctional.authengine.domain.services;

import com.emanuelvictor.api.nonfunctional.authengine.infrastructure.token.repositories.AbstractTokenRepository;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Service;


/**
 *
 */
@Service
public class CustomTokenServices extends AbstractTokenServices {

    /**
     * @param tokenRepository      AbstractTokenRepository
     * @param clientDetailsService ClientDetailsService
     */
    public CustomTokenServices(final AbstractTokenRepository tokenRepository,
                               final ClientDetailsService clientDetailsService) {
        super(tokenRepository, clientDetailsService);
    }

}
