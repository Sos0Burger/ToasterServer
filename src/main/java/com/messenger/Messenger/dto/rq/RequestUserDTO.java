package com.messenger.Messenger.dto.rq;

import com.messenger.Messenger.dao.UserDAO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RequestUserDTO {

    @NotNull
    @Schema(description = "Почта пользователя", example = "email@email.com")
    private String email;
    @NotNull
    @Schema(description = "Пароль пользователя в ЗАШИФРОВАННОМ виде", example = "jfsjfskmf32424k2")
    private String password;

    public UserDAO toDAO() {
        return new UserDAO(null,
                email,
                password,
                null,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                null,
                null,
                null,
                null,
                null);
    }
}
