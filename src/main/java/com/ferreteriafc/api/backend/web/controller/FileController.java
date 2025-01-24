package com.ferreteriafc.api.backend.web.controller;

import java.net.URLConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.ferreteriafc.api.backend.domain.service.IFileService;
import com.ferreteriafc.api.backend.web.dto.response.ImageUploadDTO;

@RestController
@RequestMapping("/images")
public class FileController {

    private final IFileService fileService;

    @Autowired
    public FileController(IFileService fileService) {
        this.fileService = fileService;
    }


    @GetMapping("/{fileName}")
    public ResponseEntity<?> handleFileDownload(@PathVariable String fileName) {
        byte[] file = fileService.downloadFile(fileName);
        String mimeType = URLConnection.guessContentTypeFromName(fileName);

        var httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.parseMediaType(mimeType));

        return ResponseEntity
                    .ok()
                    .headers(httpHeaders)
                    .body(file);
    }

    @PostMapping()
    public ResponseEntity<?> handleFileUpload(@RequestPart(name = "image") MultipartFile file) {
        var response = new ImageUploadDTO("Image uploaded.", fileService.uploadFile(file));

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
