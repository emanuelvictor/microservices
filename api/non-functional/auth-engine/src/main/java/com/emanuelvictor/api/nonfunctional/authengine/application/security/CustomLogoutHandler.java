package com.emanuelvictor.api.nonfunctional.authengine.application.security;

import com.emanuelvictor.api.nonfunctional.authengine.domain.services.ServiceToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

    /**
     *
     */
    private final ServiceToken serviceToken;

    /**
     * @param httpServletRequest  HttpServletRequest
     * @param httpServletResponse HttpServletResponse
     * @param authentication      Authentication
     */
    @Override
    public void logout(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse, final Authentication authentication) {
        final String queryString = new String(Base64.getDecoder().decode(httpServletRequest.getQueryString()));
        final String accessToken = queryString.substring(queryString.indexOf("=") + 1, queryString.indexOf("&refresh_token="));
        final String refreshToken = queryString.substring(queryString.indexOf("&refresh_token=") + 15, queryString.length());
        if (authentication != null && authentication.getDetails() != null && authentication.getDetails() instanceof WebAuthenticationDetails)
            serviceToken.removeTokens(queryString);
    }
}
