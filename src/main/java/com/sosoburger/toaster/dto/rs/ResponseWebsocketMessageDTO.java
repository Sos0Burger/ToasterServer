package com.sosoburger.toaster.dto.rs;

import com.sosoburger.toaster.state.ActionEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ResponseWebsocketMessageDTO {
    Integer id;
    @Schema(description = "Текст сообщения", example = "Привет!")
    private String text;

    private FriendDTO sender;

    private FriendDTO receiver;

    @Schema(description = "Дата сообщения", example = "1684789200000")
    private Long date;

    private List<ResponseFileDTO> attachments;

    private Boolean read;
    private ActionEnum action;
}
