package com.messenger.Messenger.dto.rs;

import com.messenger.Messenger.dao.UserDAO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseUserDTO {
    Integer id;
    @Schema(description = "Почта пользователя", example = "email@email.com")
    private String email;
    @Schema(description = "Отображаемое имя", example = "NAGIBATOR228")
    private String nickName;
}
