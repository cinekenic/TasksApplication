package com.crud.tasks.controller;

import com.crud.tasks.domain.Mail;
import com.crud.tasks.service.SimpleEmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/email")
public class EmailController {

    private final SimpleEmailService emailService;

    @PostMapping("/send")
    public void sendEmail(@RequestParam String to,
                          @RequestParam String subject,
                          @RequestParam String message,
                          @RequestParam(required = false) String cc) {

        log.info(">>> REQUEST: to={}, subject={}, message={}, cc={}", to, subject, message, cc);
        log.info("Preparing mail to: {}",to);
        log.info("Subject: {}", subject);
        log.info("Message: {}", message);
        Mail mail = new Mail(to, subject, message, cc);
        emailService.send(mail);
    }
}
