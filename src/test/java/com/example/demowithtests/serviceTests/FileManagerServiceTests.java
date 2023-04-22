package com.example.demowithtests.serviceTests;

import com.example.demowithtests.service.fileManagerService.FileManagerServiceBean;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("File Manager Service Tests")
@Disabled
public class FileManagerServiceTests {

    @InjectMocks
    private FileManagerServiceBean service;

    @Test
    void testSaveFile() {

    }

    @Test
    public void getFileTest() {

    }
}
