package com.sosoburger.toaster.service;

import com.sosoburger.toaster.dao.FileDAO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    FileDAO save(MultipartFile attachment) throws IOException;
    FileDAO findById(Integer id);
}
