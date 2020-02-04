package com.continous.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.continous.model.EmailData;
import com.continous.services.EmailService;

import reactor.core.publisher.Mono;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;
  
    @PostMapping(value = "/email", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<EmailData>> create(/*@Valid*/ @RequestBody EmailData mailData) {
        return emailService.createEmail(mailData).map(savedEmail -> ResponseEntity.ok(savedEmail));

    }

}
