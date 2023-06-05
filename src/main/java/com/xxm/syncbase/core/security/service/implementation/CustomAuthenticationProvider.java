package com.xxm.syncbase.core.security.service.implementation;

import com.xxm.syncbase.core.setting.service.SettingService;
import com.xxm.syncbase.core.usermgt.model.SystemUser;
import com.xxm.syncbase.core.usermgt.service.ApplicationUserService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);
    private SystemUser user;

    @Autowired
    private SettingService settingService;
    @Autowired
    private ApplicationUserService applicationUserService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public CustomAuthenticationProvider() {
    }

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        System.out.println(String.format("login %s and password %s", username, password));
        String hexx = this.passwordEncoder.encode(password);
        System.out.println(hexx);
        if (username != null && !username.trim().equals("")) {
            logger.debug("Authenticating user [{}]", username);
            this.user = this.applicationUserService.findUserByUsername(username);
            if (this.user == null) {
                logger.error("Username [{}] not found", username);
                throw new UsernameNotFoundException("Username [" + username + "] not found");
            } else {
                String token = "";
                boolean authenticated = this.authenticateWithDatabase(this.user, password, token);
                if (!authenticated) {
                    logger.error("Invalid credentials");
                    throw new BadCredentialsException("Invalid credentials");
                } else if (!this.user.isEnabled()) {
                    logger.error("User [{}] is disabled", username);
                    throw new DisabledException("User [" + username + "] is disabled");
                } else {
                    logger.info("Authentication successful for user [{}]", username);
                    Collection<? extends GrantedAuthority> authorities = this.getAuthorities(this.user);
                    return new UsernamePasswordAuthenticationToken(username, password, authorities);
                }
            }
        } else {
            throw new UsernameNotFoundException("Username is required");
        }
    }

    boolean authenticateWithDatabase(SystemUser user, String password, String token) {
        logger.debug("Authenticating user against internal database");
        return this.passwordEncoder.matches(password, user.getPassword());
    }

    private Collection<? extends GrantedAuthority> getAuthorities(SystemUser user) {
        List<GrantedAuthority> authorities = new ArrayList();
        this.getPermissions(user).forEach((permission) -> {
            authorities.add(new SimpleGrantedAuthority(permission));
        });
        return authorities;
    }

    private List<String> getPermissions(SystemUser user) {
        List<String> permissions = new ArrayList();
        if (user.getRole() != null) {
            user.getRole().getPermissions().forEach((permission) -> {
                permissions.add(permission.getName());
            });
        }

        return permissions;
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return this.user.isEnabled();
    }

    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}