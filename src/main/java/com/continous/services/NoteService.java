package com.continous.services;

import org.springframework.http.ResponseEntity;

import com.continous.model.Note;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface NoteService {

    Flux<Note> findAll();

    Mono<Note> save(Note note);

    Mono<Note> update(String id, Note note);

    Mono<ResponseEntity<Void>> delete(String id);

    Mono<Note> getById(String id);

}
