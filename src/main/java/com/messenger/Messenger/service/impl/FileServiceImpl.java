package com.messenger.Messenger.service.impl;

import com.messenger.Messenger.dao.FileDAO;
import com.messenger.Messenger.exception.NotFoundException;
import com.messenger.Messenger.exception.UploadException;
import com.messenger.Messenger.repository.FileRepository;
import com.messenger.Messenger.service.FileService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileRepository fileRepository;

    @SneakyThrows
    @Override
    public FileDAO save(MultipartFile attachment){
        try {
            return fileRepository.save(new FileDAO(null, attachment.getName(), attachment.getContentType(), attachment.getBytes()));
        } catch (IOException e) {
            throw new UploadException("Ошибка загрузки файлов");
        }
    }

    @SneakyThrows
    @Override
    public FileDAO findById(Integer id) {
        //Существует ли файл
        if (fileRepository.existsById(id)) {
            return fileRepository.findById(id).get();
        }
        throw new NotFoundException("Файла с таким id не существует");
    }
}
