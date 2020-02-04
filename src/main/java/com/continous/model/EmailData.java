package com.continous.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class EmailData {

    @NotBlank
    @Size(max = 50)
    private String toEmail;

    @NotBlank
    @Size(max = 50)
    private String subject;

    @NotBlank
    @Size(max = 50)
    private String text;

    public EmailData() {
    };

    public EmailData(@NotBlank @Size(max = 50) String toEmail, @NotBlank @Size(max = 50) String subject,
            @NotBlank @Size(max = 50) String text) {
        this.toEmail = toEmail;
        this.subject = subject;
        this.text = text;
    }

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "EmailData [toEmail=" + toEmail + ", subject=" + subject + ", text=" + text + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((subject == null) ? 0 : subject.hashCode());
        result = prime * result + ((text == null) ? 0 : text.hashCode());
        result = prime * result + ((toEmail == null) ? 0 : toEmail.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        EmailData other = (EmailData) obj;
        if (subject == null) {
            if (other.subject != null)
                return false;
        } else if (!subject.equals(other.subject))
            return false;
        if (text == null) {
            if (other.text != null)
                return false;
        } else if (!text.equals(other.text))
            return false;
        if (toEmail == null) {
            if (other.toEmail != null)
                return false;
        } else if (!toEmail.equals(other.toEmail))
            return false;
        return true;
    }

}
