package com.example.cloud.file.processor.file_upload_service.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${kafka.topic.file-upload}")
    private String fileUploadTopic;

    @Bean
    public NewTopic createTopic(){
        return TopicBuilder.name(fileUploadTopic).build();
    }
}
