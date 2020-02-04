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

import com.continous.model.Note;
import com.continous.services.NoteService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class NoteController {

    @Autowired
    private NoteService noteService;
  
    @GetMapping("/alive")
    public Mono<String> isAlive() {
        return Mono.just("Application is running!!!");
    }
    

    @GetMapping(value = "/notes", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Note> listNotes() {
        return noteService.findAll();
    }

    @GetMapping(value = "/notes/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Note>> getNodeById(@PathVariable(value = "id") String noteId) {
        return noteService.getById(noteId).map(savedNote -> ResponseEntity.ok(savedNote))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/notes", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Note>> create(@Valid @RequestBody Note note) {
        return noteService.save(note).map(savedNote -> ResponseEntity.ok(savedNote));

    }

    @PutMapping(value = "/notes/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Note>> getUserById(@PathVariable(value = "id") String noteId,
            @Valid @RequestBody Note note) {
        return noteService.update(noteId, note).map(updatedNote -> ResponseEntity.ok(updatedNote));
    }

    @DeleteMapping("/notes/{id}")
    public Mono<ResponseEntity<Void>> deleteUserById(@PathVariable(value = "id") String noteId) {
        return noteService.delete(noteId);
    }

}
