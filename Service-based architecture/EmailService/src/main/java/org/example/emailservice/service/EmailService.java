package org.example.emailservice.service;


import org.example.emailservice.models.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;
import java.util.List;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public boolean sendTicketEmail(List<Passenger> passengers, String subject, String text, byte[] attachment) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(passengers.get(0).getEmail());
            helper.setSubject(subject);
            helper.setText(text+"\n Best regards,\nStaff\n");


            // Add the PDF as an attachment
            helper.addAttachment("ticket.pdf", new ByteArrayResource(attachment));

            emailSender.send(message);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
