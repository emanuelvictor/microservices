package com.emanuelvictor.api.nonfunctional.authengine.domain.services;

import com.emanuelvictor.api.nonfunctional.authengine.domain.repositories.feign.IUserFeignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 2.0.0, 04/01/2020
 */
@Component
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    /**
     *
     */
    private final IUserFeignRepository userFeignRepository;

    /**
     * @param username String
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return this.userFeignRepository.loadUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not founded!"));
    }
}
