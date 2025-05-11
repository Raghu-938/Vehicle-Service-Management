package com.aits.VehicleServicesManagement.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.aits.VehicleServicesManagement.entity.Users;
import com.aits.VehicleServicesManagement.repo.UserRepository;

 
@Service
	public class CustomUserDetailsService implements UserDetailsService {
 
	    @Autowired
	    private UserRepository userRepository;
 
	    @Override
	    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	        Users user = userRepository.findByEmail(email)
	            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
	        return new CustomUserDetails(user);
	    }
	}







