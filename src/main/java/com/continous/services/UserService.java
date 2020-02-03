package com.continous.services;

import com.continous.model.User;

import reactor.core.publisher.Flux;

public interface UserService {
    Flux<User> findByUsername(String name);
}
