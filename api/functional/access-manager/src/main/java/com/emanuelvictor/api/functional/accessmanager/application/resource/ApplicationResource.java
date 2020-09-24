package com.emanuelvictor.api.functional.accessmanager.application.resource;

import com.emanuelvictor.api.functional.accessmanager.domain.entities.Application;
import com.emanuelvictor.api.functional.accessmanager.domain.services.ApplicationService;
import com.emanuelvictor.api.functional.accessmanager.infrastructure.aid.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.emanuelvictor.api.functional.accessmanager.application.resource.Roles.*;

/**
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping({APPLICATION_MAPPING_RESOURCE})
public class ApplicationResource {

    /**
     *
     */
    private final ApplicationService applicationService;

    /**
     *
     */
    //TODO make in bach in future
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{clientId}/notify")
    public Boolean notifyClient(@PathVariable final String clientId) {
        return applicationService.notify(clientId);
    }


    /**
     * @param defaultFilter String
     * @param enableFilter  Boolean
     * @param pageable      Pageable
     * @return Page<Application>
     */
    @GetMapping
    @PreAuthorize("hasAnyAuthority('" + APPLICATION_GET_ROLE + "')")
    public Page<Application> listByFilters(final String defaultFilter, final Boolean enableFilter, final Pageable pageable) {
        return this.applicationService.listByFilters(defaultFilter, enableFilter, pageable);
    }

    /**
     * @param id long
     * @return Application
     */
    @GetMapping("{id}")
//    @PreAuthorize("hasAnyAuthority('" + APPLICATION_GET_ROLE + "')")
    public Optional<Application> findById(@PathVariable final Object id) {
        if (id == null)
            return Optional.empty();
        if (id instanceof String && !Utils.isNumeric((String) id))
            return this.applicationService.loadClientByClientId((String) id);
        return this.applicationService.findById(Long.parseLong((String) id));
    }

    /**
     * @param application Application
     * @return Application
     */
    @PostMapping
    @PreAuthorize("hasAnyAuthority('" + APPLICATION_POST_ROLE + "')")
    public Application save(@RequestBody final Application application) {
        return this.applicationService.save(application);
    }

    /**
     * @param id          long
     * @param application Application
     * @return Application
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('" + APPLICATION_PUT_ROLE + "')")
    public Application updateApplication(@PathVariable final long id, @RequestBody final Application application) {
        return this.applicationService.save(id, application);
    }

//    /**
//     * @param id {long}
//     * @return boolean
//     */
//    @PutMapping("/enable")
//    @PreAuthorize("hasAnyAuthority('" + APPLICATION_PUT_ACTIVATE_ROLE + "')")
//    public boolean updateEnable(@RequestBody final long id) {
//        return this.applicationService.updateEnable(id).getEnabled();
//    }
//
//    /**
//     * @param id Long
//     */
//    @PutMapping("/update-password/{id}")
//    public void updatePassword(@PathVariable final long id, final HttpServletRequest request) {
//        final String currentPassword = request.getParameter("actualPassword");
//        final String newPassword = request.getParameter("newPassword");
//
//        this.applicationService.updatePassword(id, currentPassword, newPassword);
//    }
//
//    /**
//     * @param applicationId      long
//     * @param newPassword String
//     * @return Application
//     */
//    @GetMapping("{applicationId}/change-password")
//    @PreAuthorize("hasAnyAuthority('" + APPLICATION_PUT_CHANGE_PASSWORD_ROLE + "')")
//    Application changePassword(@PathVariable final long applicationId, @RequestParam final String newPassword) {
//        return this.applicationService.changePassword(applicationId, newPassword);
//    }

}
