package com.continous;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.continous.model.Note;

import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ContnotesApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

//    @Autowired
//    private MongoTemplate mongoTemplate;

    @Test
    public void testCreateNote() {
        Note note1 = new Note();
        note1.setContents("note 1");
          webTestClient.post().uri("/notes").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).body(Mono.just(note1), Note.class).exchange().expectStatus()
                .isOk().expectHeader().contentType(MediaType.APPLICATION_JSON).expectBody().jsonPath("$.id")
                .isNotEmpty().jsonPath("$.contents").isEqualTo("note 1");

    }

}
