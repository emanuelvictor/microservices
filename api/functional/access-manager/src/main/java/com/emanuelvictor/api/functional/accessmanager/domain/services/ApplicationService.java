package com.emanuelvictor.api.functional.accessmanager.domain.services;

import com.emanuelvictor.api.functional.accessmanager.application.resource.ApplicationResource;
import com.emanuelvictor.api.functional.accessmanager.domain.entities.Application;
import com.emanuelvictor.api.functional.accessmanager.domain.entities.Permission;
import com.emanuelvictor.api.functional.accessmanager.domain.entities.User;
import com.emanuelvictor.api.functional.accessmanager.domain.logics.application.ApplicationSavingLogic;
import com.emanuelvictor.api.functional.accessmanager.domain.logics.application.ApplicationUpdatingLogic;
import com.emanuelvictor.api.functional.accessmanager.domain.repositories.ApplicationRepository;
import com.emanuelvictor.api.functional.accessmanager.domain.repositories.PermissionRepository;
import com.emanuelvictor.api.functional.accessmanager.infrastructure.misc.PasswordGenerator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 2.0.0, 04/01/2020
 */
@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplicationService {

    public static final Logger LOG = LoggerFactory.getLogger(ApplicationResource.class);

    private final PasswordEncoder passwordEncoder;

    private final PermissionRepository permissionRepository;
    private final ApplicationRepository applicationRepository;

    private final List<ApplicationSavingLogic> applicationSavingLogics;
    private final List<ApplicationUpdatingLogic> userUpdatingLogics;

    /**
     * @param defaultFilter String
     * @param enableFilter  Boolean
     * @param pageable      Pageable
     * @return Page<Application>
     */
    public Page<Application> listByFilters(final String defaultFilter, final Boolean enableFilter, final Pageable pageable) {
        return applicationRepository.listByFilters(defaultFilter, pageable);
    }

    /**
     * @param application Usuario
     */
    @Transactional
    public Application save(final Application application) {

        Assert.isNull(application.getId(), "Você não tem acesso a essa aplicação");

        this.applicationSavingLogics.forEach(logic -> logic.perform(application));

        return this.applicationRepository.save(application);
    }

    /**
     * @param application Usuario
     */
    @Transactional
    public Application save(final long id, final Application application) {

        this.applicationRepository.findById(id).ifPresentOrElse(actual -> {
            this.userUpdatingLogics.forEach(logic -> logic.perform(application));

            this.applicationRepository.save(application);
        }, () -> {
            throw new IllegalArgumentException("O id não pode ser nulo para atualização");
        });

        return application;

    }

    /**
     * @param id long
     */
    @Transactional
    public void delete(final long id) {
        this.applicationRepository.findById(id).ifPresent(application -> {
            if (application.getClientId().equals("admin")) {
                throw new IllegalArgumentException("Impossivel deletar o Administrador");
            } else {
                this.applicationRepository.delete(application);
            }
        });
    }

    /**
     * @param id long
     * @return String
     */
    @Transactional
    public String changePassword(final long id) {

        final String password = PasswordGenerator.generate();

        this.applicationRepository.findById(id)
                .ifPresent(application -> {
                    application.setClientSecret(this.passwordEncoder.encode(password));
                    this.applicationRepository.save(application);
                });

        return password;
    }

    /**
     * @param clientId String
     * @return {@link Optional<Application>}
     */
    public Optional<Application> loadClientByClientId(final String clientId) {
        final Optional<Application> applicationOptional = this.applicationRepository.findByClientId(clientId);

        applicationOptional.ifPresent(this::removeLowerPermissions);

        return applicationOptional;
    }

    /**
     * todo remover
     * Remove lowerPermissions to keep only the upperPermissions (rootPermissions).
     *
     * @param application {@link Application}
     */
    private void removeLowerPermissions(final Application application) {
//        application.getGroup().getGroupPermissions().forEach(groupPermission -> groupPermission.getPermission().setLowerPermissions(null));
    }

    /**
     * @return List<Aplicacao>
     */
    public List<Application> findAll() {
        return this.applicationRepository.findAll();
    }

    /**
     * @param filtro   String
     * @param pageable Pageable
     * @return Page<Aplicaca
     */
    public Page<Application> findByFiltro(final String filtro, final Pageable pageable) {
        return applicationRepository.findByFiltro(filtro, pageable);
    }

    /**
     * @param id Long
     * @return Optional<Aplicacao>
     */
    public Optional<Application> findById(final long id) {
        return this.applicationRepository.findById(id);
    }

    /**
     * @return Page<Permissao>
     */
    public Page<Permission> findAllPermissions() {
        return permissionRepository.findAll(PageRequest.of(0, 100000));
    }

    /**
     * @param clientId String
     */
    public Boolean notify(final String clientId) {
        LOG.info("Notifying " + clientId);
        return true;
    }
}
