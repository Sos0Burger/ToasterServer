package com.messenger.Messenger.dto.rs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FriendDTO {

    Integer id;
    @Schema(description = "Отображаемое имя", example = "NAGIBATOR228", nullable = true)
    private String nickName;

    @Schema(description = "Аватар профиля", example = "https://i.imgur.com/AD3MbBi.jpeg", nullable = true)
    private String image;
}
