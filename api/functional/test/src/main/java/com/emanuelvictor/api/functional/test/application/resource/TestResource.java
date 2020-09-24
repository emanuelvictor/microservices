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
@RequestMapping("api/testing")
public class TestResource {

    /**
     * @return
     */
    @GetMapping
    @PreAuthorize("hasAuthority('root')")
    public String buscarPorCodigo() {
        return "accessed";
    }

    /**
     * @return
     */
    @GetMapping("not-access")
//    @PreAuthorize("hasAuthority('asdfasdfasdf') AND #oauth2.hasScope('read')")
    @PreAuthorize("hasAuthority('asdfasdfasdf')")
    public StringBuilder notAccess() {
        return new StringBuilder("not-accessed");
    }

    /**
     * @return
     */
    @GetMapping("public-access")
    public StringBuilder publicAccess() {
        return new StringBuilder("public-accessed");
    }

}
