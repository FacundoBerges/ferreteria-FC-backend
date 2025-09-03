package com.ferreteriafc.api.backend.domain.service.implementation;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ferreteriafc.api.backend.domain.service.IFileService;
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

        String originalFilename = file.getOriginalFilename();

        if (originalFilename == null || originalFilename.isEmpty())
            throw new InvalidImageFileException("Invalid file name provided.");

        if (!isValidImage(originalFilename))
            throw new InvalidImageFileException("File is not a valid image. Supported extensions are: jpg, jpeg, png, webp, svg.");

        try {
            byte[] fileBytes = file.getBytes();

            if (!isValidImageContent(fileBytes))
                throw new InvalidImageFileException("File is not a valid image content.");

            String uploadDirectory = env.getProperty("upload.dir");

            if (uploadDirectory == null || uploadDirectory.isEmpty())
                throw new InvalidImageFileException("No upload path provided.");

            Path uploadPath = Path.of(uploadDirectory);
            Path uploadDirectoryPath = Files.createDirectories(uploadPath);

            String fileExtension = getFileExtension(originalFilename);
            String fileName = UUID.randomUUID() + (fileExtension.isEmpty() ? "" : "." + fileExtension);
            Path filePath = uploadDirectoryPath.resolve(fileName);

            Files.write(filePath, fileBytes);

            return fileName;
        } catch (IOException e) {
            throw new InvalidImageFileException("File could not be uploaded: " + e.getMessage());
        }
    }

    @Override
    public byte[] downloadFile(String fileName) {
        if (fileName == null || fileName.isEmpty())
            throw new InvalidImageFileException("Invalid file name provided.");

        if (fileName.contains("..") || fileName.contains("/") || fileName.contains("\\"))
            throw new InvalidImageFileException("Invalid file name provided.");

        byte[] file;
        String uploadDirectory = env.getProperty("upload.dir");

        if (uploadDirectory == null || uploadDirectory.isEmpty())
            throw new InvalidImageFileException("No upload path provided.");

        Path uploadPath = Path.of(uploadDirectory);
        Path filePath = uploadPath.resolve(fileName);

        try {
            if (!Files.exists(filePath) || !filePath.normalize().startsWith(uploadPath.normalize()))
                throw new InvalidImageFileException("File not found or access denied.");

            file = Files.readAllBytes(filePath);
        }
        catch (NoSuchFileException e) {
            throw new InvalidImageFileException("File not found: " + fileName);
        }
        catch (IOException e) {
            throw new InvalidImageFileException("File could not be downloaded: " + e.getMessage());
        }

        return file;
    }

    private boolean isValidImage(String originalFilename) {
        if (originalFilename == null || originalFilename.isEmpty())
            return false;

        String fileExtension = getFileExtension(originalFilename).toLowerCase();

        if (fileExtension.isEmpty())
            return false;

        return  ( fileExtension.equals("png")
                || fileExtension.equals("jpeg")
                || fileExtension.equals("jpg")
                || fileExtension.equals("svg")
                || fileExtension.equals("webp") );
    }

    private boolean isValidImageContent(byte[] fileBytes) {
        try (InputStream inputStream = new ByteArrayInputStream(fileBytes)) {
            BufferedImage image = ImageIO.read(inputStream);

            return image != null;
        } catch (IOException e) {
            return false;
        }
    }

    private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');

        return lastDotIndex > 0
                ? fileName.substring(lastDotIndex + 1).toLowerCase()
                : "";
    }

}
