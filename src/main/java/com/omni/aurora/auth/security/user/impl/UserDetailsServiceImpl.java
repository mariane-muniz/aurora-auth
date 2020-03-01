package com.omni.aurora.auth.security.user.impl;

import com.omni.aurora.core.model.ApplicationUser;
import com.omni.aurora.core.repository.ApplicationUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Objects;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ApplicationUserRepository applicationUserRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        log.info("Searching user in DB, username: " + username);
        final ApplicationUser user = this.applicationUserRepository.findByUsername(username);
        if (Objects.isNull(user))
            throw new UsernameNotFoundException("Application user " + username + " not found.");

        return new CustomUserDetails(user);
    }

    private static final class CustomUserDetails extends ApplicationUser implements UserDetails {

        CustomUserDetails(final @NotNull ApplicationUser applicationUser) {
            super(applicationUser);
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_" + this.getRole());
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}
