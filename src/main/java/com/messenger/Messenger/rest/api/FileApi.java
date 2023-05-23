package com.messenger.Messenger.rest.api;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/file")
public interface FileApi {
    @Operation(summary = "Загрузка файла")
    @PostMapping(value = "/upload")
    ResponseEntity<?> upload(@RequestParam("attachments") MultipartFile[] attachments);
    @GetMapping("/{id}")
    ResponseEntity<?> getFile(@PathVariable Integer id);
}
