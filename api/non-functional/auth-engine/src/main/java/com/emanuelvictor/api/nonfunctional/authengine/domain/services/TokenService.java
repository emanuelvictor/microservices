package com.emanuelvictor.api.nonfunctional.authengine.domain.services;

import com.emanuelvictor.api.nonfunctional.authengine.infrastructure.token.domain.repositories.AbstractTokenRepository;
import com.emanuelvictor.api.nonfunctional.authengine.infrastructure.token.domain.services.AbstractTokenService;
import com.emanuelvictor.api.nonfunctional.authengine.infrastructure.token.domain.services.RevokeTokenDomainService;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


/**
 * todo It's not necessary.
 */
@Service
public class TokenService extends AbstractTokenService {

    /**
     * @param clientDetailsService
     * @param abstractTokenRepository
     * @param revokeTokenDomainService
     */
    public TokenService(final ClientDetailsService clientDetailsService,
                        final AbstractTokenRepository abstractTokenRepository,
                        final RevokeTokenDomainService revokeTokenDomainService) {
        super(clientDetailsService, abstractTokenRepository, revokeTokenDomainService);
    }
}
