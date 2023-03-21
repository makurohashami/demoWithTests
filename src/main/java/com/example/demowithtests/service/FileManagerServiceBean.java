package com.example.demowithtests.service;

import com.example.demowithtests.util.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileManagerServiceBean implements FileManagerService {

    @Value("${avatars.path}")
    private String pathToFile;

    @Override
    public String saveFile(MultipartFile fileToSave) throws IOException {
        Path path = Paths.get(pathToFile);
        if (!Files.exists(path))
            Files.createDirectories(path);

        var finalName = UUID.randomUUID() + "_" + fileToSave.getOriginalFilename();

        Files.copy(fileToSave.getInputStream(), path.resolve(finalName));
        return finalName;
    }

    @Override
    public byte[] getFile(String fileName) throws IOException {
        File file = new File(pathToFile + fileName);
        if (!file.exists()) {
            throw new ResourceNotFoundException();
        }
        return Files.readAllBytes(file.toPath());
    }
}
