package com.example.cloud.file.processor.file_upload_service.repository;

import com.example.cloud.file.processor.file_upload_service.model.FileUpload;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileUploadRepository extends JpaRepository<FileUpload, Long> {
}
