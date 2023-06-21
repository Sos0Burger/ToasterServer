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
    private String nickName;
    @Schema(description = "Список друзей", example = "1,2,4")
    private List<FriendDTO> friendsList;
    @Schema(description = "Аватар профиля", example = "https://i.imgur.com/AD3MbBi.jpeg")
    private String image;
}
