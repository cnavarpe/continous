package com.continous.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.continous.model.Note;
import com.continous.repository.NoteRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class NoteServiceImpl implements NoteService {
    
    private static final long sleepTime =10000;

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private EmailService mailService;

    @Override
    public Flux<Note> findAll() {
         try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            
        }
         return noteRepository.findAll();
    }

    @Override
    public Mono<Note> getById(String id) {
        //return noteRepository.findById(id).doOnSuccess(T->{});
        //flatMap or map, we can use map and return value
        return noteRepository.findById(id);
    }

    @Override
    public Mono<Note> save(Note note) {
        return noteRepository.save(note).doOnSuccess(savedNote -> {
            mailService.sendModificationEmail(note.getUsername(),"New Note", "Note "+note.getId()+"has been added");
        });
    }

    @Override
    public Mono<Note> update(String id, Note note) {
       
        return noteRepository.findById(id).flatMap(dbNote -> {
            dbNote.setContents(note.getContents());
            mailService.sendModificationEmail(note.getUsername(),"Update Note", "Note "+note.getId()+"has been updated");
            return noteRepository.save(dbNote);
        });
    }

    @Override
    public Mono<ResponseEntity<Void>> delete(String id) {
        return noteRepository.findById(id).flatMap(dbNote -> {
            return noteRepository.delete(dbNote).then(Mono.just(ResponseEntity.ok().<Void>build()));
        }).defaultIfEmpty(ResponseEntity.notFound().build());

    }
}
