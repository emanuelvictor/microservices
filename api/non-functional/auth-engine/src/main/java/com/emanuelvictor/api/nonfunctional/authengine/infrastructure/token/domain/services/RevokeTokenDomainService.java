package com.emanuelvictor.api.nonfunctional.authengine.infrastructure.token.domain.services;

public interface RevokeTokenDomainService {

    void revokeToken(String token);
}
