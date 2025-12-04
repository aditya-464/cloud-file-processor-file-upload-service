package com.example.cloud.file.processor.file_upload_service.controller;

import com.example.cloud.file.processor.file_upload_service.exception.NoFileException;
import com.example.cloud.file.processor.file_upload_service.model.FileUpload;
import com.example.cloud.file.processor.file_upload_service.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class FileUploadController {

    private final FileStorageService fileStorageService;

    @Autowired
    public FileUploadController(FileStorageService fileStorageService){
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/welcome")
    public String welcome(){
        return "Jai Shree Radha!";
    }

    @PostMapping("/upload")
    public ResponseEntity<FileUpload> uploadFile(@RequestParam("file")MultipartFile file) throws Exception{
        if(file.isEmpty()){
            throw new NoFileException("No file selected");
        }
        FileUpload fileUpload = fileStorageService.upload(file);
        return new ResponseEntity<>(fileUpload, HttpStatus.OK);
    }
}
