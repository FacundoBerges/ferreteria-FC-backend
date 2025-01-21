package com.ferreteriafc.api.backend.domain.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletContext;

import com.ferreteriafc.api.backend.web.exception.InvalidImageFileException;

@Service
public class FileServiceImpl implements IFileService {

    private final ServletContext servletContext;
    private final String UPLOAD_PATH = "resources/uploads";

    @Autowired
    public FileServiceImpl(ServletContext servletContext) {
        this.servletContext = servletContext;
    }


    @Override
    public String uploadFile(MultipartFile file) {
        if (file == null || file.isEmpty())
            return null;

        String fileName = file.getResource().getFilename();

        if (fileName == null || fileName.isEmpty() || ! isValidImage(file))
            throw new InvalidImageFileException("File is not a valid image. Supported extensions are: jpg, jpeg, png, webp, svg.");

        String absolutePath = servletContext.getRealPath(UPLOAD_PATH);

        if (absolutePath == null || absolutePath.isEmpty())
            return null;

        String filePath = absolutePath + File.separator + fileName;

        try {
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            throw new InvalidImageFileException("File could not be uploaded. Please try again.");
        }

        return filePath;
    }

    @Override
    public byte[] downloadFile(String filePath) {
        byte[] file;

        try {
            file = Files.readAllBytes(new File(filePath).toPath());
        }catch (IOException e) {
            throw new InvalidImageFileException("File could not be downloaded. Please try again.");
        }

        return file;
    }

    private boolean isValidImage(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();

        if (originalFilename == null || originalFilename.isEmpty())
            return false;

        int index = originalFilename.lastIndexOf(".");

        if (index == -1)
            return false;

        String fileExtension = originalFilename.substring(index).toLowerCase();

        return  ( fileExtension.endsWith("png")
                || fileExtension.endsWith("jpeg")
                || fileExtension.endsWith("jpg")
                || fileExtension.endsWith("svg")
                || fileExtension.endsWith("webp") );
    }

}
