package com.messenger.Messenger.service;

import com.messenger.Messenger.dao.FileDAO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    FileDAO save(MultipartFile attachment) throws IOException;
    FileDAO findById(Integer id);
}
