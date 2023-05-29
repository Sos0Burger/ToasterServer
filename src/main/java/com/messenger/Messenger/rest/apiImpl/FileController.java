package com.messenger.Messenger.rest.apiImpl;

import com.messenger.Messenger.exception.ExceptionMessage;
import com.messenger.Messenger.rest.api.FileApi;
import com.messenger.Messenger.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class FileController implements FileApi {
    @Autowired
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @Override
    public ResponseEntity<?> upload(MultipartFile attachment) {
        try {
            return fileService.save(attachment);
        } catch (IOException e) {
            return new ResponseEntity<>(new ExceptionMessage("Ошибка загрузки файлов"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> getFile(Integer id) {
        return fileService.findById(id);
    }
}
