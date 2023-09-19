package com.icloud.service.impl;

import com.icloud.service.MailService;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {
    private final JavaMailSender mailSender;
    private String from;

    public MailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public boolean sendMail(String to, String subject, String text) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("1805303088@qq.com");
        msg.setTo(to);
        msg.setSubject(subject);
        msg.setText(text);
        try{
            mailSender.send(msg);
        } catch (MailException ex){
            System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }
}
