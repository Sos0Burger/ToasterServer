package com.messenger.Messenger.dto.rs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseUserDTO {
    Integer id;
    @Schema(description = "Отображаемое имя", example = "NAGIBATOR228")
    private String nickname;
    @Schema(description = "Список друзей")
    private List<FriendDTO> friends;
    @Schema(description = "Аватар профиля")
    private ResponseFileDTO image;
}
