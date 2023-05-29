package com.messenger.Messenger.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    ResponseEntity<?> save(MultipartFile attachment) throws IOException;
    ResponseEntity<?> findById(Integer id);
}
