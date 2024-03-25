package com.messenger.Messenger.rest.api;

import com.messenger.Messenger.dto.rs.ResponseFileDTO;
import com.messenger.Messenger.exception.NotFoundException;
import com.messenger.Messenger.exception.UploadException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/file")
public interface FileApi {
    @Operation(summary = "Загрузка файла")
    @PostMapping(value = "/upload")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Файл успешно загружен",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Integer.class)))),
            @ApiResponse(responseCode = "500", description = "Ошибка записи файлов",
                    content = @Content(schema = @Schema(implementation = UploadException.class))
            )
    })
    ResponseEntity<ResponseFileDTO> upload(@RequestPart MultipartFile attachment);

    @Operation(summary = "Получение файла по ID")
    @GetMapping(value = "/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Файл успешно получен",
                    content = @Content(schema = @Schema(implementation = Byte[].class))),
            @ApiResponse(responseCode = "404", description = "Файл с таким ID не найден",
                    content = @Content(schema = @Schema(implementation = NotFoundException.class))
            )
    })
    ResponseEntity<byte[]> getFile(@PathVariable Integer id);
}
