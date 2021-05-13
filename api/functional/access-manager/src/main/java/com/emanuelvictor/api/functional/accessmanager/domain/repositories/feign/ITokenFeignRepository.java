package com.emanuelvictor.api.functional.accessmanager.domain.repositories.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 1.0.0, 10/09/2019
 */
@FeignClient(name = "tokens", url = "${oauth.endpoints.tokens}")
public interface ITokenFeignRepository {

    /**
     * @param name String
     * @return Set<String>
     */
    @GetMapping("{name}")
    Set<Object> findTokenByName(@PathVariable("name") final String name);

    /**
     * @param token String
     */
    @DeleteMapping("{token}")
    void revoke(@PathVariable("token") final String token);

}
