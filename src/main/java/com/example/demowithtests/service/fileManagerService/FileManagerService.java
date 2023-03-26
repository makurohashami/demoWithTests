package com.example.demowithtests.service.fileManagerService;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileManagerService {

    String saveFile(MultipartFile fileToSave) throws IOException;

    byte[] getFile(String fileName) throws IOException;
}
