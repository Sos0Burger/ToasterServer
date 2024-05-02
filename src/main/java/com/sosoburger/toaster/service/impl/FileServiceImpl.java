package com.sosoburger.toaster.service.impl;

import com.sosoburger.toaster.dao.FileDAO;
import com.sosoburger.toaster.dao.TagDAO;
import com.sosoburger.toaster.exception.NotFoundException;
import com.sosoburger.toaster.exception.UploadException;
import com.sosoburger.toaster.repository.FileRepository;
import com.sosoburger.toaster.repository.TagRepository;
import com.sosoburger.toaster.retrofit.ImaggaApiImpl;
import com.sosoburger.toaster.retrofit.ResponseTagsDTO;
import com.sosoburger.toaster.retrofit.UploadResult;
import com.sosoburger.toaster.service.FileService;
import lombok.SneakyThrows;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Objects;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private TagRepository tagRepository;

    private final ImaggaApiImpl imaggaApi = new ImaggaApiImpl();
    private final String auth = "Basic YWNjX2IyMTc4NGY2YTNiNzgxNjpkYzgyZTczMjVhYTA2N2QxZTRjMTY1ZWYxNzBhYmYyZg==";

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

            var saved =  fileRepository.save(new FileDAO(
                    null,
                    attachment.getOriginalFilename(),
                    attachment.getContentType(),
                    attachment.getBytes(), hash,
                    new ArrayList<>())
            );
            getTags(saved);
            return saved;
        } catch (IOException | NoSuchAlgorithmException e) {
            throw new UploadException("Ошибка загрузки файлов");
        }
    }

    @Override
    public FileDAO findById(Integer id) {
        //Существует ли файл
        if (fileRepository.existsById(id)) {
            return fileRepository.findById(id).get();
        }
        throw new NotFoundException("Файла с таким id не существует");
    }

    private void getTags(FileDAO file){
        var response = imaggaApi.upload(
                MultipartBody.Part.createFormData(
                        "image",
                        file.getName(),
                        RequestBody.create(file.getData())), auth);
        response.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<UploadResult> call, Response<UploadResult> response) {
                if (response.isSuccessful()) {
                    var tagResponse = imaggaApi.getTags(response.body().getResult().getUpload_id(), auth);
                    tagResponse.enqueue(new Callback<>() {
                        @Override
                        public void onResponse(Call<ResponseTagsDTO> call, Response<ResponseTagsDTO> response) {
                            if (response.isSuccessful()) {
                                response.body().getResult().getTags().forEach(item -> tagRepository.save(new TagDAO(null, item.getTag().getRu(), file)));
                            } else {
                                System.out.println("Не удалось получить теги, код: " + response.code());
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseTagsDTO> call, Throwable throwable) {
                            System.out.println("Не удалось получить теги");
                        }
                    });
                } else {
                    System.out.println("Не удалось загрузить, код: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<UploadResult> call, Throwable throwable) {
                System.out.println("Не удалось загрузить изображение");
            }
        });
    }
    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02X", b));
        }
        return result.toString();
    }
}
