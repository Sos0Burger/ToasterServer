package com.sosoburger.toaster.service.impl;

import com.sosoburger.toaster.dao.FileDAO;
import com.sosoburger.toaster.exception.NotFoundException;
import com.sosoburger.toaster.exception.UploadException;
import com.sosoburger.toaster.repository.FileRepository;
import com.sosoburger.toaster.service.FileService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileRepository fileRepository;


    @SneakyThrows
    @Override
    public FileDAO save(MultipartFile attachment) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            var hash = bytesToHex(messageDigest.digest(attachment.getBytes()));
            var file = fileRepository.findByHash(hash);
            if (
                    file != null &&
                    Objects.equals(file.getType(), attachment.getContentType()) &&
                    Objects.equals(file.getName(), attachment.getOriginalFilename())
            ) {
                return file;
            }
            return fileRepository.save(new FileDAO(null, attachment.getOriginalFilename(), attachment.getContentType(), attachment.getBytes(), hash));
        } catch (IOException | NoSuchAlgorithmException e) {
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

    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02X", b));
        }
        return result.toString();
    }
}
