package com.example.fintech.authentications;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class UserPrincipal implements UserDetails {

    private String id;
    private String name;
    private String email;

    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(String id, String name, Collection<? extends GrantedAuthority> authorities, String email) {
        super();
        this.id = id;
        this.name = name;
        this.authorities = authorities;
        this.email = email;
    }

    public UserPrincipal() {
    }

    public static UserPrincipal create(TokenUser tokenUser) {
        List<GrantedAuthority> authorities = tokenUser.getAuthorities().stream().map((scope) -> new SimpleGrantedAuthority(scope)).collect(Collectors.toList());

        return new UserPrincipal(
                tokenUser.getId(),
                tokenUser.getName(),
                authorities,
                tokenUser.getEmail()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return name;
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
