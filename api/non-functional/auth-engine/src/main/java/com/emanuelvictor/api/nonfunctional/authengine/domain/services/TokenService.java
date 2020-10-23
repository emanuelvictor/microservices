package com.emanuelvictor.api.nonfunctional.authengine.domain.services;

import com.emanuelvictor.api.nonfunctional.authengine.infrastructure.token.domain.repositories.AbstractTokenRepository;
import com.emanuelvictor.api.nonfunctional.authengine.infrastructure.token.domain.services.AbstractTokenService;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


/**
 *
 */
@Service
public class TokenService extends AbstractTokenService {

    /**
     *
     */
    private final RestTemplate restTemplate;

    /**
     * @param oAuth2RestTemplate      OAuth2RestTemplate
     * @param clientDetailsService    ClientDetailsService
     * @param abstractTokenRepository AbstractTokenRepository
     */
    public TokenService(final OAuth2RestTemplate oAuth2RestTemplate,
                        final ClientDetailsService clientDetailsService,
                        final AbstractTokenRepository abstractTokenRepository) {
        super(clientDetailsService, abstractTokenRepository);
        this.restTemplate = oAuth2RestTemplate;
    }

    /**
     * @param url   String
     * @param token String
     */
    @Override
    public void delete(final String url, final String token) {
        restTemplate.delete(url + "/" + token);
    }
}
