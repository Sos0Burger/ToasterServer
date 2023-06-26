package com.messenger.Messenger.rest.api;

import com.messenger.Messenger.dto.rq.RequestAuth;
import com.messenger.Messenger.dto.rq.RequestUserDTO;
import com.messenger.Messenger.dto.rs.FriendDTO;
import com.messenger.Messenger.dto.rs.ResponseUserDTO;
import com.messenger.Messenger.dto.rs.UserSettingsDTO;
import com.messenger.Messenger.exception.ExceptionMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/user")
public interface UserApi {
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Все ок"),
            @ApiResponse(
                    responseCode = "409",
                    description = "Пользователь с такой почтой уже существует",
                    content = {
                            @Content(schema = @Schema(implementation = ExceptionMessage.class))
                    }
            )
    })
    @Operation(summary = "Создание пользователя")
    @PostMapping
    ResponseEntity<ResponseUserDTO> create(@Validated @RequestBody RequestUserDTO requestUserDTO);

    @Operation(summary = "Получение данных пользователя")
    @GetMapping("/{id}")
    ResponseEntity<ResponseUserDTO> getUser(@PathVariable("id") Integer id);

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "ID пользователя",
                    content = {
                            @Content(schema = @Schema(implementation = Integer.class))
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "Аккаунт не найден",
                    content = {
                            @Content(schema = @Schema(implementation = ExceptionMessage.class))
                    }
            )
    })
    @Operation(summary = "Авторизация")
    @GetMapping("/auth")
    ResponseEntity<Integer> auth(@RequestHeader(value = "email") String email, @RequestHeader(value = "hash") String hash);

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Пользователь успешно добавлен"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Пользователь не найден",
                    content = {
                            @Content(schema = @Schema(implementation = ExceptionMessage.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Вы уже отправили запрос в друзья этому пользователю," +
                            " Вы уже добавили этого пользователя в друзья," +
                            " Иди в дурку проверся(запрос в друзья самому себе)",
                    content = {
                            @Content(schema = @Schema(implementation = ExceptionMessage.class))
                    }
            )
    })
    @Operation(summary = "Отправка заявки в друзья")
    @PostMapping("/friend-request/{senderid}/{receiverid}")
    ResponseEntity<FriendDTO> sendFriendRequest(@PathVariable(name = "senderid") Integer senderid, @PathVariable(name = "receiverid") Integer receiverid);

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Запрос успешно принят"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Пользователь не отправлял запрос в друзья, Пользователь с таким ID не существует",
                    content = {
                            @Content(schema = @Schema(implementation = ExceptionMessage.class))
                    }
            )
    })
    @Operation(summary = "Принять запрос в друзья")
    @PostMapping("/friends/{receiverid}/{senderid}")
    ResponseEntity<FriendDTO> acceptFriendRequest(@PathVariable(name = "receiverid") Integer receiverid, @PathVariable(name = "senderid") Integer senderid);

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Список друзей пользователя",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(
                                                    implementation = ResponseUserDTO.class)
                                    )
                            )
                    }),
            @ApiResponse(responseCode = "404",
                    description = "Пользователь не найден",
                    content = {
                            @Content(
                                    schema = @Schema(implementation = ExceptionMessage.class)
                            )
                    })
    })
    @Operation(summary = "Список друзей")
    @GetMapping("/{id}/friends")
    ResponseEntity<List<FriendDTO>> getFriends(@PathVariable("id") Integer id);

    @Operation(summary = "Получить список входящих заявок")
    @GetMapping("/{id}/pending")
    ResponseEntity<List<FriendDTO>> getPending(@PathVariable("id") Integer id);

    @Operation(summary = "Получить список исходящих заявок")
    @GetMapping("/{id}/sent")
    ResponseEntity<List<FriendDTO>> getSent(@PathVariable("id") Integer id);

    @Operation(summary = "Обновить данные пользователя")
    @PutMapping("/{id}/picture")
    ResponseEntity<?> updatePicture(@PathVariable("id") Integer id, @RequestBody RequestAuth auth, @RequestHeader("url") String url);

    @Operation(summary = "Обновить отображаемое имя")
    @PutMapping("/{id}/nickname")
    ResponseEntity<?> updateNickname(@PathVariable("id") Integer id, @RequestBody RequestAuth auth, @RequestHeader("nickname") String nickname);

    @Operation(summary = "Получить текущие настройки")
    @GetMapping("/{id}/settings")
    ResponseEntity<UserSettingsDTO> getSettings(@PathVariable("id") Integer id);

    @Operation(summary = "Обновить токен Firebase")
    @PutMapping("{id}/firebase")
    ResponseEntity<?> updateFirebaseToken(@PathVariable("id") Integer id, @RequestParam String token);
}
