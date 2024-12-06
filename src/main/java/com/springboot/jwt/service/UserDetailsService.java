package com.springboot.jwt.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserDetailsService {
    UserDetails loadByUserName(String userEmail) throws UsernameNotFoundException;
}
