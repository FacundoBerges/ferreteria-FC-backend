package com.ferreteriafc.api.backend.domain.service;

import com.ferreteriafc.api.backend.web.exception.InvalidImageFileException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements IFileService {

    private String uploadDirectory = "uploads";

    @Override
    public String uploadFile(MultipartFile file) {
        if (file.isEmpty()) {
            return null;
        }

        if (! isValidImage(file)) {
            throw new InvalidImageFileException("File is not a valid image. Supported extensions are: jpg, jpeg, png, webp, svg.");
        }

        String fileName = file.getResource().getFilename();

        return "";
    }

    private boolean isValidImage(MultipartFile file) {
        String fileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));

        if (    ! fileExtension.endsWith("png")
            ||  ! fileExtension.endsWith("jpeg")
            ||  ! fileExtension.endsWith("jpg")
            ||  ! fileExtension.endsWith("svg")
            ||  ! fileExtension.endsWith("webp")) {
            return false;
        }

        return true;
    }

}
