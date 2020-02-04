package com.continous.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.continous.model.User;
import com.continous.services.UserService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/usersname/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<User> listUsers(@PathVariable(value = "username") String username) {
        return userService.findByUsername(username);
    }

    @GetMapping(value = "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<User>> getNodeById(@PathVariable(value = "id") String noteId) {
        return userService.getById(noteId).map(savedUser -> ResponseEntity.ok(savedUser))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<User>> create(@Valid @RequestBody User note) {
        return userService.save(note).map(savedUser -> ResponseEntity.ok(savedUser));

    }

    @PutMapping(value = "/users/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<User>> getUserById(@PathVariable(value = "id") String id,
            @Valid @RequestBody User note) {
        return userService.update(id, note).map(updatedUser -> ResponseEntity.ok(updatedUser));
    }

    @DeleteMapping("/users/{id}")
    public Mono<ResponseEntity<Void>> deleteUserById(@PathVariable(value = "id") String id) {
        return userService.delete(id);
    }

}
