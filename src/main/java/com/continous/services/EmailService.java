package com.continous.services;

import com.continous.model.EmailData;

import reactor.core.publisher.Mono;

public interface EmailService {

    Mono<EmailData> createEmail(EmailData emailData);

}
