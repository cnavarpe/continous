package com.continous.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.continous.model.User;
import com.continous.repository.UserRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    

    @Override
    public Flux<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Flux<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Mono<User> save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Mono<User> update(String id, User user) {
        return userRepository.findById(id).flatMap(dbUser -> {
            dbUser.setEmail(user.getEmail());
                 return userRepository.save(dbUser);
        });
    }

    @Override
    public Mono<ResponseEntity<Void>> delete(String id) {
        return userRepository.findById(id).flatMap(dbUser -> {
            return userRepository.delete(dbUser).then(Mono.just(ResponseEntity.ok().<Void>build()));
        }).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Override
    public Mono<User> getById(String id) {
        return userRepository.findById(id);
    }

}
