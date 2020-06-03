package com.gadomska.EmailSender;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.xml.bind.ValidationException;

@Controller
@RequestMapping("/feedback")
public class FeedbackController {

    private EmailConfig emailConfig;

    public FeedbackController(EmailConfig emailConfig) {
        this.emailConfig = emailConfig;
    }

    public void sendFeedback(@RequestBody Feedback feedback, BindingResult bindingResult) throws ValidationException {
        if (bindingResult.hasErrors()) {
            throw new ValidationException("Feedback is not valid");
        }

        //mail sender creation
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(this.emailConfig.getHost());
        mailSender.setPort(this.emailConfig.getPort());
        mailSender.setUsername(this.emailConfig.getUsername());
        mailSender.setPassword(this.emailConfig.getPassword());

        //email instance creation
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(feedback.getEmail());
        mailMessage.setTo("jsandragadomska@feedback.com");
        mailMessage.setSubject("New feedback from " + feedback.getName());
        mailMessage.setText(feedback.getFeedback());

        //send mail
        mailSender.send(mailMessage);
    }

}
