package com.messenger.Messenger.dto.rs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseFileDTO {
    @Schema(description = "Имя файла", example = "Кот")
    private String name;

    @Schema(description = "Адресс файла", example = "Понятия не имею")
    private String URL;

    @Schema(description = "Тип файла", example = "")
    private String type;

    @Schema(description = "Раземер файла", example = "1337")
    private long size;
}
