package com.emanuelvictor.api.functional.assessment.application.context;

import com.emanuelvictor.api.functional.assessment.domain.entities.Unity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;

/**
 * Class ContextHolder, serve para resolver o usuário autenticado e compara o mesmo com outro.
 *
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 1.0.0, 10/09/2019
 */
public class ContextHolder {

    /**
     * @return {User}
     */
    public static Unity getAuthenticatedUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof Unity) {
            return (Unity) authentication.getPrincipal();
        }

        throw new AuthenticationCredentialsNotFoundException("O usuário não está autenticado");
    }

    /**
     * @return boolean
     */
    public static boolean isAuthenticated() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return authentication != null && authentication.getPrincipal() != null && !authentication.getPrincipal().equals("anonymousUser");
    }

    /**
     * @return boolean
     */
    public static boolean itsMe(final long id) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Assert.isTrue(isAuthenticated(), "O usuário não está autenticado");

        return ((Unity) authentication.getPrincipal()).getId().equals(id);
    }

}
