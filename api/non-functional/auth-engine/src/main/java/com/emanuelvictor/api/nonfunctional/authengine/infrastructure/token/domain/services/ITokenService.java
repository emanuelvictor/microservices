package com.emanuelvictor.api.nonfunctional.authengine.infrastructure.token.domain.services;

import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

public interface ITokenService extends AuthorizationServerTokenServices, ResourceServerTokenServices, ConsumerTokenServices {

    /**
     *
     */
    void delete(final String url, final String token);

}
