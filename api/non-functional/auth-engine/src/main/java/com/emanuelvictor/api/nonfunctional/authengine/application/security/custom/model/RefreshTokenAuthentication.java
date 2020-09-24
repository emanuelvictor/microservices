package com.emanuelvictor.api.nonfunctional.authengine.application.security.custom.model;


import lombok.Data;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

@Data
public class RefreshTokenAuthentication {

    private OAuth2RefreshToken token;

    private OAuth2Authentication authentication;

    public RefreshTokenAuthentication( final OAuth2RefreshToken token, final OAuth2Authentication authentication) {
        this.token = token;
        this.authentication = authentication;
    }
}
