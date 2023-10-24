package com.emanuelvictor.api.functional.accessmanager.application.resource;

import com.emanuelvictor.api.functional.accessmanager.domain.entities.User;
import com.emanuelvictor.api.functional.accessmanager.domain.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

/**
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("users")
public class UserResource {

    /**
     *
     */
    private final UserService userService;

    /**
     * @param defaultFilter String
     * @param enableFilter  Boolean
     * @param pageable      Pageable
     * @return Page<User>
     */
    @GetMapping
    @PreAuthorize("hasAnyAuthority('root.access-manager.users.get','root.access-manager.users','root.access-manager','root')")
    public Page<User> listByFilters(final String defaultFilter, final Boolean enableFilter, final Pageable pageable) {
        return this.userService.listByFilters(defaultFilter, enableFilter, pageable);
    }

    /**
     * @param id long
     * @return User
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('root.access-manager.users.get','root.access-manager.users','root.access-manager','root')")
    public User findById(@PathVariable final long id) {
        return this.userService.findById(id);
    }

    /**
     * @param user User
     * @return User
     */
    @PostMapping
    @PreAuthorize("hasAnyAuthority('root.access-manager.users.post','root.access-manager.users','root.access-manager','root')")
    public User save(@RequestBody final User user) {
        return this.userService.save(user);
    }

    /**
     * @param id   long
     * @param user User
     * @return User
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('root.access-manager.users.put','root.access-manager.users','root.access-manager','root')")
    public User updateUser(@PathVariable final long id, @RequestBody final User user) {
        return this.userService.save(id, user);
    }

    /**
     * @param id {long}
     * @return boolean
     */
    @PutMapping("/enable")
    @PreAuthorize("hasAnyAuthority('root.access-manager.users.put.activate','root.access-manager.users.put','root.access-manager.users','root.access-manager','root')")
    public boolean updateEnable(@RequestBody final long id) {
        return this.userService.updateEnable(id).getEnabled();
    }

    /**
     * @param id Long
     */
    @PutMapping("/update-password/{id}")
//    @PreAuthorize("hasAnyAuthority('root.access-manager.users.put.change-password','root.access-manager.users.put','root.access-manager.users','root.access-manager','root')")
    public void updatePassword(@PathVariable final long id, final HttpServletRequest request) {
        final String currentPassword = request.getParameter("actualPassword");
        final String newPassword = request.getParameter("newPassword");

        this.userService.updatePassword(id, currentPassword, newPassword);
    }

    /**
     * TODO sem permissionamento
     *
     * @return UserDetails
     */
    @GetMapping("{username}/username") // TODO mudar para load
    public Optional<User> loadUserByUsername(@PathVariable final String username) {
        return userService.loadUserByUsername(username);
    }

    /**
     * @param userId      long
     * @param newPassword String
     * @return User
     */
    @GetMapping("{userId}/change-password")
    @PreAuthorize("hasAnyAuthority('root.access-manager.users.put.change-password','root.access-manager.users.put','root.access-manager.users','root.access-manager','root')")
    User changePassword(@PathVariable final long userId, @RequestParam final String newPassword) {
        return this.userService.changePassword(userId, newPassword);
    }

}
