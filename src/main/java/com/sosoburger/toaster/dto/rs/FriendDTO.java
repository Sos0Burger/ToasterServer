package com.sosoburger.toaster.dto.rs;

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
    private String nickname;

    @Schema(description = "Аватар профиля", nullable = true)
    private ResponseFileDTO image;
}
