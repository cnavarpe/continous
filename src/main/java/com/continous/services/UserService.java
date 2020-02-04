package com.continous.services;

import org.springframework.http.ResponseEntity;

import com.continous.model.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Flux<User> findByUsername(String username);
    
    Flux<User> findAll();
    
    Mono<User> save(User user);

    Mono<User> update(String id, User user);

    Mono<ResponseEntity<Void>> delete(String id);

    Mono<User> getById(String id);
}
