package com.emanuelvictor.api.functional.assessment.domain.entities.generic;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 2.0.0, 01/01/2020
 */
public class RevisionListener implements org.hibernate.envers.RevisionListener {

    /**
     * {@inheritDoc }
     *
     * @param revisionEntity
     */
    @Override
    public void newRevision(Object revisionEntity) {

        final Revision revision = (Revision) revisionEntity;

        revision.setCreatedOn(new Date());
        revision.setCreatedBy(this.getLoggedUser());
    }

    /**
     * Get the username of the logged user
     *
     * @return the username of the logged user
     */
    private String getLoggedUser() {

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            return authentication.getName();
        }
        return "unknown";
    }
}
