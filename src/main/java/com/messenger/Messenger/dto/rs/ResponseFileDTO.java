package com.messenger.Messenger.dto.rs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseFileDTO {
    private Integer id;
    @Schema(description = "Имя файла", example = "Кот")
    private String name;

    @Schema(description = "Тип файла", example = "application/doc")
    private String type;

    @Schema(description = "Размер файла", example = "1337")
    private long size;
}
