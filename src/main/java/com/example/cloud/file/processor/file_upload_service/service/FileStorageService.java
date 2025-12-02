package com.example.cloud.file.processor.file_upload_service.service;

import com.example.cloud.file.processor.file_upload_service.kafka.producer.FileUploadTopic;
import com.example.cloud.file.processor.file_upload_service.model.FileUpload;
import com.example.cloud.file.processor.file_upload_service.repository.FileUploadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class FileStorageService {

    private final FileUploadRepository fileUploadRepository;
    private final S3Service s3Service;
    private final FileUploadTopic fileUploadTopic;

    public FileUpload upload(MultipartFile file) throws Exception {

        // Save temporarily to local
        Path tempFile = Files.createTempFile("upload-", file.getOriginalFilename());
        file.transferTo(tempFile.toFile());

        // Upload to S3
        String s3Url = s3Service.uploadFile(tempFile, file.getContentType());

        // Save metadata to DB
        FileUpload uploaded = FileUpload.builder()
                .originalName(file.getOriginalFilename())
                .s3Url(s3Url)
                .size(file.getSize())
                .status("UPLOADED")
                .build();

        FileUpload saved = fileUploadRepository.save(uploaded);
        fileUploadTopic.sendMessage("{ \"fileId\": " + saved.getId() + ", \"s3Url\": \"" + s3Url + "\" }");

        return saved;
    }
}
