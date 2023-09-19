package com.icloud.service;

public interface MailService {
    boolean sendMail(String to, String from, String text);
}
