package com.messenger.Messenger.dto.rs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessageDTO {
    @Schema(description = "Текст сообщения", example = "Привет!")
    private String text;

    ResponseUserDTO sender;

    ResponseUserDTO receiver;

    @Schema(description = "Дата сообщения", example = "1684789200000")
    private long date;

    List<String> attachments;
}
