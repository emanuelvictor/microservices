package com.emanuelvictor.api.functional.bertosimulator.domain.services;

import com.emanuelvictor.api.functional.bertosimulator.application.context.ContextHolder;
import com.emanuelvictor.api.functional.bertosimulator.application.i18n.MessageSourceHolder;
import com.emanuelvictor.api.functional.bertosimulator.domain.entities.User;
import com.emanuelvictor.api.functional.bertosimulator.domain.repositories.GroupPermissionRepository;
import com.emanuelvictor.api.functional.bertosimulator.domain.repositories.UserRepository;
import com.emanuelvictor.api.functional.bertosimulator.infrastructure.aid.StandaloneBeanValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Objects;
import java.util.Optional;

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 2.0.0, 04/01/2020
 */
@Component
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final GroupPermissionRepository groupPermissionRepository;

    /**
     * @param defaultFilter String
     * @param enableFilter  Boolean
     * @param pageable      Pageable
     * @return Page<User>
     */
    public Page<User> listByFilters(final String defaultFilter, final Boolean enableFilter, final Pageable pageable) {
        return userRepository.listByFilters(defaultFilter, enableFilter, pageable);
    }

    /**
     * @param username long
     * @return User
     * <p>
     */
    @Transactional(readOnly = true)
    public Optional<User> loadUserByUsername(final String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * @param id long
     * @return User
     */
    @Transactional(readOnly = true)
    public User findById(final long id) {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(MessageSourceHolder.getMessage("repository.notFoundById", id)));
    }

    /**
     * @param user User
     * @return User
     */
    @Transactional
    public User save(final User user) {

        user.setEnabled(true);

        user.setPassword(this.passwordEncoder.encode(User.DEFAULT_PASSWORD));

        return this.userRepository.save(user);
    }

    /**
     * @param user User
     * @return User
     */
    @Transactional
    public User save(final long id, final User user) {

        user.setId(id);

        final User userSaved = this.userRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException(MessageSourceHolder.getMessage("repository.notFoundById", user.getId())));

        user.setPassword(userSaved.getPassword());

        return this.userRepository.saveAndFlush(user);
    }

    /**
     * @param id long
     * @return User
     */
    @Transactional
    public User updateEnable(final long id) {

        final User userSaved = this.userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(MessageSourceHolder.getMessage("repository.notFoundById", id)));

        Assert.notNull(userSaved, MessageSourceHolder.getMessage("repository.notFoundById", id));

        userSaved.setEnabled(!userSaved.getEnabled());

        return this.userRepository.saveAndFlush(userSaved);
    }

    /**
     * @param id              long
     * @param currentPassword String
     * @param newPassword     String
     */
    @Transactional
    public void updatePassword(final long id, final String currentPassword, final String newPassword) {

        final User authenticatedUser = ContextHolder.getAuthenticatedUser();

        Assert.isTrue(authenticatedUser.getId().equals(id), MessageSourceHolder.getMessage("security.accessDenied"));
        final User user = this.userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(MessageSourceHolder.getMessage("repository.notFoundById", id)));

        Assert.notNull(currentPassword, "A senha atual não pode ser vazia.");
        Assert.notNull(newPassword, "A nova senha não pode ser vazia.");

        Assert.isTrue(BCrypt.checkpw(currentPassword, user.getPassword()), "A senha atual está incorreta.");

        //somente para fins de validação, sem econdar a senha
        user.setPassword(newPassword);
        StandaloneBeanValidation.validate(user);

        user.setPassword(this.passwordEncoder.encode(newPassword));

        this.userRepository.saveAndFlush(user);
    }

    /**
     * Gera e-mail de recuperação de senha
     *
     * @param username String
     */
    @Transactional
    public void recoverPassword(final String username) {

        Assert.notNull(username, "O email deve ser preenchido");
        final User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not founded!"));

        Assert.notNull(user, "Usuário não encontrado");

        if (!user.isEnabled()) {
            throw new DisabledException("Usuário inabilitado");//MessageSourceHolder.getMessage( "authentication.disabledUser", null, LocaleContextHolder.getLocale() ) );
        }

        this.userRepository.saveAndFlush(user);
    }

    /**
     * @param userId      long
     * @param newPassword String
     * @return User
     */
    @Transactional
    public User changePassword(final long userId, final String newPassword) {
        final User user = userRepository.findById(userId).orElse(null);
        Objects.requireNonNull(user).setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return user;
    }

}
