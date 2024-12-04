package com.springboot.jwt.service.impl;

import com.springboot.jwt.repository.UserRepository;
import com.springboot.jwt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadByUserName(String userName){
        return userRepository.findByEmail(userName);
    }


}
