package com.emanuelvictor.api.functional.bertosimulator.application.resource;

import com.emanuelvictor.api.functional.bertosimulator.domain.entities.Application;
import com.emanuelvictor.api.functional.bertosimulator.domain.services.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("applications")
public class ApplicationResource {

    /**
     *
     */
    private final ApplicationService applicationService;

    /**
     * TODO SEM PERMISSÃO?
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
    @PreAuthorize("hasAnyAuthority('root.access-manager.applications.get','root.access-manager.applications','root.access-manager','root')")
    public Page<Application> listByFilters(final String defaultFilter, final Boolean enableFilter, final Pageable pageable) {
        return applicationService.listByFilters(defaultFilter, enableFilter, pageable);
    }

    /**
     * @param id long
     * @return Application
     */
    @GetMapping("{id}")
//    @PreAuthorize("hasAnyAuthority('root.access-manager.applications.get','root.access-manager.applications','root.access-manager','root')")
    public Optional<Application> findById(@PathVariable final Long id) {
        return applicationService.findById(id);
    }

    /**
     * @param clienteId long
     * @return Application
     */
    @GetMapping("{clienteId}/load")
//    @PreAuthorize("hasAnyAuthority('root.access-manager.applications.get','root.access-manager.applications','root.access-manager','root')")
    public Optional<Application> findById(@PathVariable final String clienteId) {
        return applicationService.loadClientByClientId(clienteId);
    }

    /**
     * @param application Application
     * @return Application
     */
    @PostMapping
    @PreAuthorize("hasAnyAuthority('root.access-manager.applications.post','root.access-manager.applications','root.access-manager','root')")
    public Application save(@RequestBody final Application application) {
        return applicationService.save(application);
    }

    /**
     * @param id          long
     * @param application Application
     * @return Application
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('root.access-manager.applications.put','root.access-manager.applications','root.access-manager','root')")
    public Application updateApplication(@PathVariable final long id, @RequestBody final Application application) {
        return applicationService.save(id, application);
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
