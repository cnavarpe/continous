package com.continous;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.continous.model.EmailData;
import com.continous.model.Note;
import com.continous.repository.NoteRepository;
import com.continous.services.EmailService;
import com.continous.services.NoteService;

import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ContnotesApplicationTests {

    @Autowired
    private WebTestClient webTestClient;
    
    @Autowired
    private NoteRepository noteRepository;
    
    @MockBean
    private NoteService noteService;
    
    
    @MockBean
    private EmailService emailService;
    
    @Test
    public void testCreateNote() {
        Note note = new Note("Testing Note", "Sarah");
        Mockito.when(noteService.save(note)).thenReturn(Mono.just(note));
        webTestClient.
        post().
        uri("/notes").
        contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .body(Mono.just(note), Note.class)
        .exchange()
        .expectStatus()
        .isOk()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody()
       // .jsonPath("$.id").isNotEmpty()
        .jsonPath("$.contents").isEqualTo("Testing Note")
        .jsonPath("$.username").isEqualTo("Sarah")
        ;
    }
    
     @Test
    public void readUpdateDeleteNote() {
         
       Note note=  new Note("Testing Note 2", "Sarah");
       note.setId("2343");
         
       Mockito.when(noteService.getById(note.getId())).thenReturn(Mono.just(note));
        
        webTestClient.get()
        .uri("/notes/{id}", Collections.singletonMap("id", note.getId()))
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .jsonPath("$.id").isNotEmpty()
        .jsonPath("$.contents").isEqualTo("Testing Note 2")
        .jsonPath("$.username").isEqualTo("Sarah")
        ;
        noteRepository.delete(note);

    }
     
   
     @Test
     public void sendEmail() {
         EmailData emailData =new EmailData("test@test.com", "Testing Note", "testing contents");
         Mockito.when(emailService.createEmail(emailData)).thenReturn(Mono.just(emailData));
         webTestClient.
         post().
         uri("/email").
         contentType(MediaType.APPLICATION_JSON)
         .accept(MediaType.APPLICATION_JSON)
         .body(Mono.just(emailData), EmailData.class)
         .exchange()
         .expectStatus()
         .isOk()
         .expectHeader()
         .contentType(MediaType.APPLICATION_JSON)
         .expectBody()
         .jsonPath("$.toEmail").isEqualTo("test@test.com")
         .jsonPath("$.subject").isEqualTo("Testing Note")
         .jsonPath("$.text").isEqualTo("testing contents")
         ;
     }
  }
