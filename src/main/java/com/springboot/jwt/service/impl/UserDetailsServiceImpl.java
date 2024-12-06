package com.springboot.jwt.service.impl;

import com.springboot.jwt.entity.User;
import com.springboot.jwt.repository.UserRepository;
import com.springboot.jwt.service.UserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadByUserName(String userEmail) throws UsernameNotFoundException {
       return userRepository.findByEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException(userEmail));
    }
}
