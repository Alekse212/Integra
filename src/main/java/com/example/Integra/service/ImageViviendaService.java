package com.example.Integra.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Service
    public class ImageViviendaService {

        private final AmazonS3 amazonS3;

        @Autowired
        public ImageViviendaService(AmazonS3 amazonS3) {
            this.amazonS3 = amazonS3;
        }

        public void saveImageToS3(String type, String key, byte[] imageData) {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(imageData.length);
            metadata.setContentType(type);
            PutObjectRequest putObjectRequest = new PutObjectRequest("alexintegra", key, new ByteArrayInputStream(imageData), metadata);
            amazonS3.putObject(putObjectRequest);
            System.out.println("Image uploaded to Amazon S3 successfully.");
        }


    public byte[] getImageFromS3(String key) throws IOException {
        S3Object object = amazonS3.getObject("alexintegra", key);
        S3ObjectInputStream objectInputStream = object.getObjectContent();
        byte[] imageData = IOUtils.toByteArray(objectInputStream);
        objectInputStream.close();
        System.out.println("Изображение успешно загружено из Amazon S3.");
        return imageData;
    }
    public void deleteImageFromS3(String key) {
        amazonS3.deleteObject("alexintegra", key);
        System.out.println("Image deleted from Amazon S3 successfully.");
    }


    }


