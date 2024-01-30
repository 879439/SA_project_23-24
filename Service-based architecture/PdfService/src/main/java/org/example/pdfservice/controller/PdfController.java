package org.example.pdfservice.controller;

import jakarta.validation.Valid;
import org.example.pdfservice.models.Booking;
import org.example.pdfservice.requests.PdfRequest;
import org.example.pdfservice.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.nio.file.Paths;

@CrossOrigin(origins = {"http://localhost:4200","http://localhost:8082"})
@RestController
@RequestMapping("")
public class PdfController {
    private final PdfService pdfService;

    @Autowired
    public PdfController(PdfService pdfService){
        this.pdfService = pdfService;
    }
    @PutMapping("/")
    public ResponseEntity<?> generatePdf(@Valid @RequestBody PdfRequest pdfRequest) {
        pdfService.createAndSendTicket(pdfRequest.getBooking(),pdfRequest.getTicketInfo());
        return ResponseEntity.ok("Pdf created!");
    }
    @GetMapping("/ticket")
    public ResponseEntity<Resource> getTicket(
            @RequestParam String bookingId) {
        try {
            Path file = Paths.get("tickets/"+bookingId+".pdf").normalize();
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_PDF)
                        .body(resource);
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

    }
}
