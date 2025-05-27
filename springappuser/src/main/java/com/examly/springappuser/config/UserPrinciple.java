package com.examly.springappuser.config;

import java.util.Collection;

public class UserPrinciple implements UserDetails{
    private final User user;

    public UserPrinciple(User user){
        this.user = user;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getUserRole()));
    }
    
    @Override
    public String getPassword(){
        return user.getPassword();
    }
    @Override
    public String getUsername(){
        return user.getEmail();
    }
    @Override
    public boolean isAccountNonExpired() { return true;}
    @Override
    

}
