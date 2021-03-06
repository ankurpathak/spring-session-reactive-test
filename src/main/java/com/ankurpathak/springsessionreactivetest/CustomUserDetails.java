package com.ankurpathak.springsessionreactivetest;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;


public class CustomUserDetails implements UserDetails {

    private User user;


    public User getUser() {
        return user;
    }

    public CustomUserDetails(User user) {
        this.user = user;
    }




    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(!CollectionUtils.isEmpty(user.getRoles()))
            return AuthorityUtils.createAuthorityList(user.getRoles().toArray(new String[]{}));
        else
            return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return user.getPassword().getValue();
    }

    @Override
    public String getUsername() {
        //return String.valueOf(user.getId());
       return this.user.getEmail().getValue();
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
