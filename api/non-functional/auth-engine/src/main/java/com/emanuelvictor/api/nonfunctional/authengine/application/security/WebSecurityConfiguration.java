package com.emanuelvictor.api.nonfunctional.authengine.application.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedGrantedAuthoritiesUserDetailsService;

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 2.0.0, 31/01/2020
 */
@Order(1)
@Configuration
//@EnableWebSecurity
@RequiredArgsConstructor
//@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    /**
     *
     */
    private final UserDetailsService userDetailsService;

    /**
     *
     */
    private final CustomLogoutHandler customLogoutHandler;

    /**
     * {@inheritDoc}
     *
     * @param builder
     * @throws Exception
     */
    @Override
    protected void configure(final AuthenticationManagerBuilder builder) throws Exception {
        builder/*.authenticationProvider(this.authenticationProvider())*/.userDetailsService(userDetailsService);
    }

    /**
     * @return AuthenticationManager
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * {@inheritDoc}
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionFixation().none()
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/**"/*, "/login", "/oauth/authorize"*/)
                .permitAll()
                .and().formLogin()
                .and()
                .logout()
                .logoutUrl("/logout")
                .addLogoutHandler(customLogoutHandler)
                .permitAll().and().logout().permitAll();
    }
}
