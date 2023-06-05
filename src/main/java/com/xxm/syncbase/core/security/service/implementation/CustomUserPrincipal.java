package com.xxm.syncbase.core.security.service.implementation;

import com.xxm.syncbase.core.security.service.CustomUserDetails;
import com.xxm.syncbase.core.usermgt.model.AbstractUser;
import com.xxm.syncbase.core.usermgt.model.SystemUser;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class CustomUserPrincipal implements CustomUserDetails {
    private final AbstractUser user;

    public CustomUserPrincipal(AbstractUser user) {
        this.user = user;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList();
        this.getPermissions(this.user).forEach((permission) -> {
            authorities.add(new SimpleGrantedAuthority(permission));
        });
        return authorities;
    }

    public String getUsername() {
        return this.user.getUserName();
    }

    public String getPassword() {
        return this.user.getPassword();
    }

    private List<String> getPermissions(AbstractUser user) {
        List<String> permissions = new ArrayList();
        SystemUser systemUser = (SystemUser)user;
        if (user.getRole() != null) {
            systemUser.getRole().getPermissions().forEach((permission) -> {
                permissions.add(permission.getName());
            });
        }

        return permissions;
    }

    public AbstractUser getUser() {
        return this.user;
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

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof CustomUserPrincipal)) {
            return false;
        } else {
            CustomUserPrincipal that = (CustomUserPrincipal)o;
            return this.user.equals(that.user);
        }
    }

    public int hashCode() {
        return this.user.hashCode();
    }
}