package com.continous.services;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;

import com.continous.model.Note;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class NoteServiceTest /*implements NoteService*/ {
/*
    private MongoTemplate mongoTemplate;
    private Class<?> entityClass;
    private String collectionName;

    public NoteServiceTest(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Flux<Note> findAll() {
        List<Note> notes = mongoTemplate.findAll(Note.class, "notes");
        Flux<Note> flux = Flux.fromIterable(notes);
        return flux;
    }

    @Override
    public Mono<Note> save(Note note) {
        note.setContents(note.getContents());
        Note savedNote = mongoTemplate.insert(note, "notes");
        return Mono.just(savedNote);
    }

    @Override
    public Mono<Note> update(String id, Note note) {
        Note savedNote = mongoTemplate.save(note, "notes");
        return Mono.just(savedNote);
    }

    @Override
    public Mono<ResponseEntity<Void>> delete(String id) {
        Mono<Note> res= getById(id);
        return null;
    }

    @Override
    public Mono<Note> getById(String id) {
        Note savedNote = mongoTemplate.findById(id, Note.class, "notes");
        if (savedNote != null) {
            return Mono.just(mongoTemplate.findById(id, Note.class, "notes"));
        } else {
            return Mono.empty();
        }
    }*/

}
