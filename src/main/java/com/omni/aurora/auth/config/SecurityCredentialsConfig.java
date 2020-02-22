package com.omni.aurora.auth.config;

import com.omni.aurora.auth.security.filter.JWTUsernameAndPasswordAuthenticationFilter;
import com.omni.aurora.core.property.JWTConfiguration;
import com.omni.aurora.token.config.SecurityTokenConfig;
import com.omni.aurora.token.converter.TokenConverter;
import com.omni.aurora.token.creator.TokenCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityCredentialsConfig extends SecurityTokenConfig {

    private UserDetailsService userDetailsService;
    private TokenCreator tokenCreator;
    private TokenConverter tokenConverter;

    public SecurityCredentialsConfig(final JWTConfiguration jwtConfiguration,
                                     final @Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService,
                                     final TokenCreator tokenCreator, TokenConverter tokenConverter) {
        super(jwtConfiguration);
        this.userDetailsService = userDetailsService;
        this.tokenCreator = tokenCreator;
        this.tokenConverter = tokenConverter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilter(new JWTUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtConfiguration, tokenCreator))
                .addFilterAfter(new JWTUsernameAndPasswordAuthenticationFilter(
                        authenticationManager(), jwtConfiguration, tokenCreator), UsernamePasswordAuthenticationFilter.class);
        super.configure(http);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}