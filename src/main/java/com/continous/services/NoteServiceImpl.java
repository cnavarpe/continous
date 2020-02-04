package com.continous.services;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.continous.model.EmailData;
import com.continous.model.Note;
import com.continous.model.User;
import com.continous.repository.NoteRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.json.JSONString;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class NoteServiceImpl implements NoteService {

    private static final long sleepTime = 10000;

    @Autowired
    private NoteRepository noteRepository;

    @Value("${server.port}")
    private String port;


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

        return noteRepository.findById(id);
    }

    @Override
    public Mono<Note> save(Note note) {
        readUserEmailAndSend(note, "created new note for:", "created new note for");
        return noteRepository.save(note);

    }

    @Override
    public Mono<Note> update(String id, Note note) {

        return noteRepository.findById(id).flatMap(dbNote -> {
            dbNote.setContents(note.getContents());
            readUserEmailAndSend(note, "updated note for:", "updatednote for");
            return noteRepository.save(dbNote);
        });
    }

    @Override
    public Mono<ResponseEntity<Void>> delete(String id) {
        return noteRepository.findById(id).flatMap(dbNote -> {
            return noteRepository.delete(dbNote).then(Mono.just(ResponseEntity.ok().<Void>build()));
        }).defaultIfEmpty(ResponseEntity.notFound().build());

    }

    private void readUserEmailAndSend(Note note, String to, String text) {
        String url = "http://localhost:" + port + "/usersname/" + note.getUsername();
        WebClient client = WebClient.builder().baseUrl("http://localhost:" + port)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultUriVariables(Collections.singletonMap("url", url)).build();

        Flux<String> flux = client.get().uri("/usersname/" + note.getUsername()).retrieve().bodyToFlux(String.class);

        flux.subscribe(s -> {
            if (s != null && (!s.isEmpty())) {
                try {
                    User[] userData = new ObjectMapper().readValue(s, User[].class);
                    if (userData != null) {
                        for (User userEmail : userData) {
                            emailSend(userEmail.getEmail(), to+note.getUsername(), text+note.getUsername());
                        }
                    }
                } catch (JsonProcessingException e) {

                }
            }
        });
    }

    private void emailSend(String email, String subject, String text) {
        EmailData emailData = new EmailData();
        emailData.setSubject(subject);
        emailData.setToEmail(email);
        emailData.setText(text);

        WebClient client = WebClient.builder().baseUrl("http://localhost:" + port)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();

        client.post().uri("/email").body(Mono.just(emailData), EmailData.class).retrieve().bodyToFlux(EmailData.class);

    }

}
