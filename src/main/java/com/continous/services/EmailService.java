package com.continous.services;

public interface EmailService {

    void sendMessage(String to, String subject, String text);

    void sendModificationEmail(String username, String string, String string2);

}
