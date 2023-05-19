package com.messenger.Messenger.dto;

import com.messenger.Messenger.dao.UserDAO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RequestUserDTO {

    @Schema(description = "Почта пользователя", example = "email@email.com")
    private String email;
    @Schema(description = "Пароль пользователя в ЗАШИФРОВАННОМ виде", example = "jfsjfskmf32424k2")
    private String password;
    @Schema(description = "Отображаемое имя", example = "NAGIBATOR228")
    private String nickName;

    public UserDAO toDAO(){
        return new UserDAO(null, email = email, password = password, nickName = nickName);
    }
}
