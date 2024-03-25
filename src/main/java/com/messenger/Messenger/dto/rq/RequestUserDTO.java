package com.messenger.Messenger.dto.rq;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RequestUserDTO {

    @NotNull
    @Schema(description = "Почта пользователя", example = "email@email.com")
    private String email;
    @NotNull
    @Schema(description = "Пароль пользователя", example = "jfsjfskmf32424k2")
    private String password;
}
