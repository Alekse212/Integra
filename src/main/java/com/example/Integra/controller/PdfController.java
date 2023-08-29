package com.example.Integra.controller;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Controller
public class PdfController {

    @GetMapping("/private/generatePdf")
    @ResponseBody
    public void generatePdf(HttpServletResponse response) throws IOException {
        try {
            // Создание буфера для PDF
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, baos);

            document.open();

            // Загрузка изображения с сервера (замените на свой путь к изображению)
            BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/path/to/image.png"));
            if (image != null) {
                Image pdfImage = Image.getInstance(getBytesFromBufferedImage(image));
                document.add(pdfImage);
            }

            document.close();

            // Установка заголовков для HTTP-ответа
            response.setHeader("Content-Disposition", "attachment; filename=example.pdf");
            response.setContentType("application/pdf");

            // Отправка PDF как байтового массива
            response.getOutputStream().write(baos.toByteArray());
            response.getOutputStream().flush();
        } catch (Exception e) {
            e.printStackTrace();
            // Обработка ошибок
        }
    }

    // Преобразование BufferedImage в массив байтов
    private byte[] getBytesFromBufferedImage(BufferedImage image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        baos.flush();
        byte[] imageBytes = baos.toByteArray();
        baos.close();
        return imageBytes;
    }
}

