package com.continous.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.continous.model.User;
import com.continous.repository.UserRepository;

import reactor.core.publisher.Flux;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public Flux<User> findByUsername(String name) {
         return userRepository.findByUsername(name);
    }
}
