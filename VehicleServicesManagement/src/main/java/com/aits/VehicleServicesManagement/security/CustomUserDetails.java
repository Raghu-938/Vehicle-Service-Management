package com.aits.VehicleServicesManagement.security;

import java.util.Collection;
import java.util.List;
 
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.aits.VehicleServicesManagement.entity.Users;
 
public class CustomUserDetails implements UserDetails {
    private final Users user;
 
    public CustomUserDetails(Users user) { this.user = user; }
 
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "ROLE_" + user.getRole());
    }
 
    @Override public String getUsername() { return user.getEmail(); }
    @Override public String getPassword() { return user.getPassword(); }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
 
    public Users getUser() { return user; }
}
