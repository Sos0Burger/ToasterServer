package com.messenger.Messenger.rest.apiImpl;

import com.messenger.Messenger.dao.FileDAO;
import com.messenger.Messenger.dto.rs.ResponseFileDTO;
import com.messenger.Messenger.exception.ExceptionMessage;
import com.messenger.Messenger.exception.UploadException;
import com.messenger.Messenger.rest.api.FileApi;
import com.messenger.Messenger.service.FileService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
    public ResponseEntity<byte[]> getFile(Integer id) {
        FileDAO file = fileService.findById(id);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", file.getType());
        return new ResponseEntity<>(file.getData(), responseHeaders, HttpStatus.OK);
    }
}
