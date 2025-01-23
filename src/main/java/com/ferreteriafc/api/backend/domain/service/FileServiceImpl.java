package com.ferreteriafc.api.backend.domain.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ferreteriafc.api.backend.web.exception.InvalidImageFileException;

@Service
public class FileServiceImpl implements IFileService {

    private final Environment env;

    @Autowired
    public FileServiceImpl(Environment env) {
        this.env = env;
    }


    @Override
    public String uploadFile(MultipartFile file) {
        if (file == null || file.isEmpty())
            throw new InvalidImageFileException("No file provided.");

        String fileName = file.getOriginalFilename();

        if (fileName == null || fileName.isEmpty() || ! isValidImage(fileName))
            throw new InvalidImageFileException("File is not a valid image. Supported extensions are: jpg, jpeg, png, webp, svg.");

        String uploadDirectory = env.getProperty("upload.dir");

        if (uploadDirectory == null || uploadDirectory.isEmpty())
            throw new InvalidImageFileException("No upload path provided.");

        Path uploadPath = Path.of(uploadDirectory);

        try {
            Path uploadDirectoryPath = Files.createDirectories(uploadPath);
            Path filePath = uploadDirectoryPath.resolve(fileName);

            Files.write(filePath, file.getBytes());
        } catch (IOException e) {
            throw new InvalidImageFileException("File could not be uploaded: " + e.getMessage());
        }

        return fileName;
    }

    @Override
    public byte[] downloadFile(String fileName) {
        byte[] file;
        String uploadDirectory = env.getProperty("upload.dir");

        if (uploadDirectory == null || uploadDirectory.isEmpty())
            throw new InvalidImageFileException("No upload path provided.");

        Path filePath = Path.of(uploadDirectory + "/" + fileName);

        try {
            file = Files.readAllBytes(filePath);
        }catch (IOException e) {
            throw new InvalidImageFileException("File could not be downloaded. Please try again.");
        }

        return file;
    }

    private boolean isValidImage(String originalFilename) {
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
