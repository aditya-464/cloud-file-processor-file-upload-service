package com.example.cloud.file.processor.file_upload_service.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class FileUploadTopic {

    @Value("${kafka.topic.file-upload}")
    private String fileUploadTopic;

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public FileUploadTopic(KafkaTemplate<String, String> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message){
        kafkaTemplate.send(fileUploadTopic, message);
    }
}
