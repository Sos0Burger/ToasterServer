package com.messenger.Messenger.rest.api;

import com.messenger.Messenger.dto.rq.RequestAuth;
import com.messenger.Messenger.dto.rq.RequestUserDTO;
import com.messenger.Messenger.dto.rs.ResponseUserDTO;
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
    ResponseEntity<?> create(@Validated @RequestBody RequestUserDTO requestUserDTO);

    @Operation(summary = "Получение данных пользователя")
    @GetMapping("/{id}")
    ResponseEntity<?> getUser(@PathVariable("id") Integer id);

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Список всех пользователей",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(
                                                    implementation = ResponseUserDTO.class)
                                    )
                            )
                    })
    })
    @Operation(summary = "Получение всех пользователей")
    @GetMapping
    ResponseEntity<?> getAll();

    @Operation(summary = "Удаление пользователя по id")
    @DeleteMapping("{id}")
    ResponseEntity<?> delete(@PathVariable(name = "id") Integer id);

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
    ResponseEntity<?> auth(@RequestHeader(value = "email") String email, @RequestHeader(value = "hash") String hash);

    @Operation(summary = "Получение данных группы пользователей")
    @GetMapping("/ids")
    ResponseEntity<?> getByIds(@RequestParam List<Integer> ids);

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
    ResponseEntity<?> sendFriendRequest(@PathVariable(name = "senderid") Integer senderid, @PathVariable(name = "receiverid") Integer receiverid);

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
    ResponseEntity<?> acceptFriendRequest(@PathVariable(name = "receiverid") Integer receiverid, @PathVariable(name = "senderid") Integer senderid);

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
    ResponseEntity<?> getFriends(@PathVariable("id") Integer id);

    @Operation(summary = "Получить список входящих заявок")
    @GetMapping("/{id}/pending")
    ResponseEntity<?> getPending(@PathVariable("id") Integer id);

    @Operation(summary = "Получить список исходящих заявок")
    @GetMapping("/{id}/sent")
    ResponseEntity<?> getSent(@PathVariable("id") Integer id);

    @Operation(summary = "Обновить данные пользователя")
    @PutMapping("/{id}/picture")
    ResponseEntity<?> updatePicture(@PathVariable("id") Integer id, @RequestBody RequestAuth auth, @RequestHeader("url") String url);

    @Operation(summary = "Обновить отображаемое имя")
    @PutMapping("/{id}/nickname")
    ResponseEntity<?> updateNickname(@PathVariable("id") Integer id, @RequestBody RequestAuth auth, @RequestHeader("nickname") String nickname);

    @Operation(summary = "Получить текущие настройки")
    @GetMapping("/{id}/settings")
    ResponseEntity<?> getSettings(@PathVariable("id") Integer id);
}
