package com.messenger.Messenger.service.impl;

import com.messenger.Messenger.dao.FileDAO;
import com.messenger.Messenger.exception.ExceptionMessage;
import com.messenger.Messenger.repository.FileRepository;
import com.messenger.Messenger.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileRepository fileRepository;

    @Override
    public ResponseEntity<?> save(MultipartFile attachment) throws IOException {
        return new ResponseEntity<>(fileRepository.save(new FileDAO(null, attachment.getName(), attachment.getContentType(), attachment.getBytes())).toDTO(),
        HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> findById(Integer id) {
        //Существует ли файл
        if (fileRepository.existsById(id)) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Content-Type", fileRepository.findById(id).get().getType());
            return new ResponseEntity<>(fileRepository.findById(id).get().getData(), responseHeaders, HttpStatus.OK);
        }
        return new ResponseEntity<>(new ExceptionMessage("Файла с таким id не существует"), HttpStatus.NOT_FOUND);
    }
}
