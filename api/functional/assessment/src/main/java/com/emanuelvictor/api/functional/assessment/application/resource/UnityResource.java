package com.emanuelvictor.api.functional.assessment.application.resource;

import com.emanuelvictor.api.functional.assessment.domain.entities.Unity;
import com.emanuelvictor.api.functional.assessment.domain.services.UnityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.emanuelvictor.api.functional.assessment.application.resource.Roles.*;

/**
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping({UNITY_MAPPING_RESOURCE})
public class UnityResource {

    /**
     *
     */
    private final UnityService unityService;

    /**
     * @param defaultFilter String
     * @param enableFilter  Boolean
     * @param pageable      Pageable
     * @return Page<User>
     */
    @GetMapping
    @PreAuthorize("hasAnyAuthority('" + UNITY_GET_ROLE + "')")
    public Page<Unity> listByFilters(final String defaultFilter, final Boolean enableFilter, final Pageable pageable) {
        return this.unityService.listByFilters(defaultFilter, enableFilter, pageable);
    }

    /**
     * @param id long
     * @return User
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('" + UNITY_GET_ROLE + "')")
    public Unity findById(@PathVariable final long id) {
        return this.unityService.findById(id);
    }

    /**
     * @param unity User
     * @return User
     */
    @PostMapping
    @PreAuthorize("hasAnyAuthority('" + UNITY_POST_ROLE + "')")
    public Unity save(@RequestBody final Unity unity) {
        return this.unityService.save(unity);
    }

    /**
     * @param id   long
     * @param unity User
     * @return User
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('" + UNITY_PUT_ROLE + "')")
    public Unity updateUser(@PathVariable final long id, @RequestBody final Unity unity) {
        return this.unityService.save(id, unity);
    }

}
