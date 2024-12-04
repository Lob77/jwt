package com.springboot.jwt.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
    UserDetails loadByUserName(String username);
}
