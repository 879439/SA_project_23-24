package org.example.backend.services;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import org.example.backend.models.Passenger;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class PdfService {
    public byte[] generateTicketPdf(TicketInfo ticketInfo) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            System.out.println("Creating pdf..");
            PdfWriter writer = new PdfWriter(byteArrayOutputStream);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            document.add(new Paragraph("Booking N: "+ticketInfo.getBookingId()).setFontSize(28).setBold());
            byte[] b = generateQRCode(ticketInfo.getBookingId(), 300,300);
            ImageData img= ImageDataFactory.create(b);
            document.add(new Image(img));
            document.add(new Paragraph("Flight Details:"+"\n"+"Type: "+ticketInfo.getType()).setFontSize(26).setBold());
            document.add(new Paragraph("Departure: "+ticketInfo.getDepartures().get(0)+"    Arrival: "+ticketInfo.getArrivals().get(0)+"      Date: "+ticketInfo.getDates().get(0)+" "+ticketInfo.getTimes().get(0)+"\n"));
            if(ticketInfo.getType().equals("round-trip")){
                document.add(new Paragraph("Departure: "+ticketInfo.getDepartures().get(1)+"    Arrival: "+ticketInfo.getArrivals().get(1)+"      Date: "+ticketInfo.getDates().get(1)+" "+ticketInfo.getTimes().get(1)+"\n"));
            }
            document.add(new Paragraph("Number of Passengers: "+ticketInfo.getPassengers().size()+"\n").setFontSize(26).setBold());
            for(Passenger p: ticketInfo.getPassengers()){
                document.add(new Paragraph("Name: "+p.getFirstname()+"\nSurname: "+p.getLastname()+"\nBirthdate: "+p.getBirthday()+"\nSeat: "+p.getSeat()));
                if(ticketInfo.getType().equals("round-trip")){
                    document.add(new Paragraph("\nSeat of return: "+p.getReturnSeat()));
                }
            }
            document.add(new Paragraph("Total price: "+String.format("%.2f",ticketInfo.getPrice())+"$").setFontSize(26).setBold());


            document.close();
        } catch (Exception e) {
            // Handle exception
        }

        return byteArrayOutputStream.toByteArray();
    }

    public byte[] generateQRCode(String text, int width, int height) throws Exception {
        // Define the hint map for the QR code generator
        Map<EncodeHintType, Object> hintMap = new HashMap<>();
        hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hintMap.put(EncodeHintType.MARGIN, 1); // default = 4
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

        // Generate a QR code as BitMatrix
        BitMatrix matrix = new MultiFormatWriter().encode(
                new String(text.getBytes("UTF-8"), "UTF-8"),
                BarcodeFormat.QR_CODE, width, height, hintMap
        );

        // Write BitMatrix to ByteArrayOutputStream
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(matrix, "PNG", byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}
