package com.continous.services;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.continous.model.User;

import reactor.core.publisher.Flux;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private UserService userService;

    private ScheduledExecutorService executorServices = Executors.newScheduledThreadPool(3);

    @Override
    public void sendMessage(String toEmail, String subject, String text) {

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
    public void sendModificationEmail(String username, String to, String body) {
        Flux<User> user = userService.findByUsername(username);
        user.doOnNext(usr -> {
            sendMessage(usr.getEmail(), to, body);
        });
    }
}
