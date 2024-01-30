package org.example.emailservice.controller;

import jakarta.validation.Valid;
import org.example.emailservice.requests.EmailRequest;
import org.example.emailservice.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:4200","http://localhost:8083"})
@RestController
@RequestMapping("")
public class EmailController {
    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService){
        this.emailService = emailService;
    }
    @PostMapping("/")
    public ResponseEntity<?> sendEmail(@Valid @RequestBody EmailRequest emailRequest) {

        if(emailService.sendTicketEmail(emailRequest.getPassengers(),emailRequest.getSubject(),emailRequest.getText(),emailRequest.getAttachment())){
            System.out.println("Email sent!!!");
            return ResponseEntity.ok("ticket sent!");
        }else{
            return ResponseEntity.badRequest().body("Error sending ticket!");
        }

    }
}
