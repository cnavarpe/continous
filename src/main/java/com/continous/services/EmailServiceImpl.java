package com.continous.services;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.continous.model.EmailData;

import reactor.core.publisher.Mono;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;

    private ScheduledExecutorService executorServices = Executors.newScheduledThreadPool(3);

    private void sendMessage(String toEmail, String subject, String text) {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom("test@test.com");
        mail.setTo(toEmail);
        mail.setSubject(subject);
        mail.setText(text);
        executorServices.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    emailSender.send(mail);
                } catch (Exception e) {

                }
            }
        });
    }

    @Override
    public Mono<EmailData> createEmail(EmailData emailData) {
        sendMessage(emailData.getToEmail(), emailData.getSubject(), emailData.getText());
        return Mono.just(emailData);
    }
}
