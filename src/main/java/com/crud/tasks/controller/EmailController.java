package com.crud.tasks.controller;

import com.crud.tasks.domain.Mail;
import com.crud.tasks.service.SimpleEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/email")
public class EmailController {

    private final SimpleEmailService emailService;

    @PostMapping("/send")
    public void sendEmail(@RequestParam String to,
                          @RequestParam String subject,
                          @RequestParam String message,
                          @RequestParam(required = false) String cc
                          ) {
        Mail mail = new Mail(to, subject, message, cc);
        emailService.send(mail);
    }
}
