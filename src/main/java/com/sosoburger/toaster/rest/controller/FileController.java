package com.sosoburger.toaster.rest.controller;

import com.sosoburger.toaster.dao.FileDAO;
import com.sosoburger.toaster.dto.rs.ResponseFileDTO;
import com.sosoburger.toaster.exception.UploadException;
import com.sosoburger.toaster.rest.api.FileApi;
import com.sosoburger.toaster.service.FileService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
public class FileController implements FileApi {
    @Autowired
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @SneakyThrows
    @Override
    public ResponseEntity<ResponseFileDTO> upload(MultipartFile attachment) {
        try {
            return new ResponseEntity<>(fileService.save(attachment).toDTO(), HttpStatus.CREATED);
        } catch (IOException e) {
            throw new UploadException("Ошибка загрузки файлов");
        }
    }

    @Override
    public ResponseEntity<InputStreamResource> getFile(Integer id) {
        FileDAO file = fileService.findById(id);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", file.getType());
        InputStream inputStream = new ByteArrayInputStream(file.getData());
        return new ResponseEntity<>(new InputStreamResource(inputStream), responseHeaders, HttpStatus.OK);
    }
}
