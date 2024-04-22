package com.sosoburger.toaster.dto.rs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessageDTO {
    Integer id;
    @Schema(description = "Текст сообщения", example = "Привет!")
    private String text;

    private FriendDTO sender;

    private FriendDTO receiver;

    @Schema(description = "Дата сообщения", example = "1684789200000")
    private Long date;

    private List<ResponseFileDTO> attachments;
}
