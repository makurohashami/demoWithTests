package com.example.demowithtests.service;

public interface ImageFetcherService {
    byte[] fetchImage(String imageUrl) throws Exception;
}
