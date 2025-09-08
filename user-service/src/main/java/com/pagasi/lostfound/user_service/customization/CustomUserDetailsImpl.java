package com.pagasi.lostfound.user_service.customization;

import com.pagasi.lostfound.user_service.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetailsImpl implements UserDetails {
    private final UserEntity user;

    public CustomUserDetailsImpl(UserEntity user) {
        this.user = user;
    }

    public Long getId() {
        return this.user.getId();
    }

    public String getName() {
        return this.user.getName();
    }

    public String getUniqueName() { return this.user.getUsername(); };

    public String getUsername() {
        return this.user.getMobileNumber();
    }

    public String getPassword() {
        return this.user.getPassword();
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.user.getRole().name()));
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
        return true;
    }
}
