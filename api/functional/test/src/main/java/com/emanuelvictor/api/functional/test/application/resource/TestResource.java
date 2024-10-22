package com.emanuelvictor.api.functional.test.application.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Willian Brecher
 * @version 1.0.0
 * @since 1.0.0, 14/01/2019
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("v1")
public class TestResource {

    /**
     * @return {@link DTO}
     */
    @GetMapping
    @PreAuthorize("hasAnyAuthority('root', 'root.juridico', 'root.juridico.parecer', 'root.juridico.parecer.enviar')")
    public DTO getAccess() {
        return new DTO("Authorized access accessed");
    }

    /**
     * @return {@link DTO}
     */
    @GetMapping("not-access")
    @PreAuthorize("hasAuthority('asdfasdfasdf')")
    public DTO cannotHaveAccess() {
        return new DTO("Forbidden access accessed");
    }

    /**
     * @return {@link DTO}
     */
    @GetMapping("public-access")
    public DTO publicAccess() {
        return new DTO("Public access accessed");
    }

}
