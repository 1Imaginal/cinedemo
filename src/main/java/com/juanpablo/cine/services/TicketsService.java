package com.juanpablo.cine.services;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.juanpablo.cine.models.Asiento;
import com.juanpablo.cine.models.Ticket;
import com.juanpablo.cine.repository.TicketRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

@Service
public class TicketsService {

    @Autowired
    TicketRepository ticketRepository;

    @Transactional
    public void eliminarTicket(long id){
        Ticket ticket = ticketRepository.findById(id).orElseThrow(()->new RuntimeException("No se encontro el ticket"));

        Asiento asiento = ticket.getAsiento();
        asiento.setDisponible(true);

        ticketRepository.delete(ticket);
    }

    public String generateQRCodeBase64(String text) throws Exception {
        QRCodeWriter barcodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = barcodeWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200);
        BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        byte[] imageBytes = baos.toByteArray();

        return Base64.getEncoder().encodeToString(imageBytes);
    }
}
