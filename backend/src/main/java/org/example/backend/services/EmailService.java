package org.example.backend.services;

import org.example.backend.models.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendTicketEmail(List<Passenger> passengers, String subject, String text, byte[] attachment) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(passengers.get(0).getEmail());
            helper.setSubject(subject);
            helper.setText(text+"\n Best regards,\nStaff\n");


            // Add the PDF as an attachment
            helper.addAttachment("ticket.pdf", new ByteArrayResource(attachment));

            emailSender.send(message);
        } catch (Exception e) {
            // Handle exception
        }
    }
}
