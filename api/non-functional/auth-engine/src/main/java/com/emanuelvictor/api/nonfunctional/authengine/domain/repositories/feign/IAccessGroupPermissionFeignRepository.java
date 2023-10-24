package com.emanuelvictor.api.nonfunctional.authengine.domain.repositories.feign;

import com.emanuelvictor.api.nonfunctional.authengine.domain.entities.GroupPermission;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "groups", url = "${oauth.endpoints.access-groups}")
public interface IAccessGroupPermissionFeignRepository {

    /**
     * @param id Long
     * @return Optional<Client>
     */
    @GetMapping("{id}/access-group-permissions")
    Page<GroupPermission> findAccessGroupPermissionsByUserId(@PathVariable final long id);
}
