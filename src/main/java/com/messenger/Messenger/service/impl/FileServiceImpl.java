package com.messenger.Messenger.service.impl;

import com.messenger.Messenger.dao.FileDAO;
import com.messenger.Messenger.exception.ExceptionMessage;
import com.messenger.Messenger.repository.FileRepository;
import com.messenger.Messenger.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileRepository fileRepository;

    @Override
    public ResponseEntity<?> save(List<MultipartFile> files) throws IOException {
        List<Integer> ids = new ArrayList<>();
        for (MultipartFile file : files
        ) {
            ids.add(fileRepository.save(new FileDAO(null, file.getName(), file.getContentType(), file.getBytes())).getId());
        }
        return new ResponseEntity<>(ids, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> findById(Integer id) {
        if(fileRepository.existsById(id)){
            return new ResponseEntity<>(fileRepository.findById(id).get().getData(), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ExceptionMessage("Файла с таким id не существует"), HttpStatus.NOT_FOUND);
    }
}
