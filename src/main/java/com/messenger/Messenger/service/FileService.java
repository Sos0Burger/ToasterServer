package com.messenger.Messenger.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {
    ResponseEntity<?> save(List<MultipartFile> file) throws IOException;
    ResponseEntity<?> findById(Integer id);
}
