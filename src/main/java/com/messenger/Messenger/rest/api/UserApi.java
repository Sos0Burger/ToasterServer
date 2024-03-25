package com.messenger.Messenger.rest.api;

import com.messenger.Messenger.dto.rq.RequestUserDTO;
import com.messenger.Messenger.dto.rs.FriendDTO;
import com.messenger.Messenger.dto.rs.ResponsePostDTO;
import com.messenger.Messenger.dto.rs.ResponseUserDTO;
import com.messenger.Messenger.dto.rs.UserSettingsDTO;
import com.messenger.Messenger.exception.AlreadyExistsException;
import com.messenger.Messenger.exception.NotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
                            @Content(schema = @Schema(implementation = AlreadyExistsException.class))
                    }
            )
    })
    @Operation(summary = "Создание пользователя")
    @PostMapping
    ResponseEntity<ResponseUserDTO> create(@RequestParam("token") String token, @Validated @RequestBody RequestUserDTO requestUserDTO);

    @Operation(summary = "Отправить код на почту")
    @PostMapping("/code")
    ResponseEntity<HttpStatus> sendCode(@RequestParam("email") String email);

    @Operation(summary = "Получение данных пользователя")
    @GetMapping()
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<ResponseUserDTO> getUser();

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
                            @Content(schema = @Schema(implementation = NotFoundException.class))
                    }
            )
    })
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Авторизация")
    @GetMapping("/auth")
    ResponseEntity<HttpStatus> auth();

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Пользователь успешно добавлен"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Пользователь не найден",
                    content = {
                            @Content(schema = @Schema(implementation = NotFoundException.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Вы уже отправили запрос в друзья этому пользователю," +
                            " Вы уже добавили этого пользователя в друзья," +
                            " Иди в дурку проверся(запрос в друзья самому себе)",
                    content = {
                            @Content(schema = @Schema(implementation = NotFoundException.class))
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
                            @Content(schema = @Schema(implementation = NotFoundException.class))
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
                                    schema = @Schema(implementation = NotFoundException.class)
                            )
                    })
    })
    @Operation(summary = "Список друзей")
    @GetMapping("/friends")
    ResponseEntity<List<FriendDTO>> getFriends();

    @Operation(summary = "Получить список входящих заявок")
    @GetMapping("/pending")
    ResponseEntity<List<FriendDTO>> getPending();

    @Operation(summary = "Получить список исходящих заявок")
    @GetMapping("/sent")
    ResponseEntity<List<FriendDTO>> getSent();

    @Operation(summary = "Обновить данные пользователя")
    @PutMapping("/picture")
    ResponseEntity<?> updatePicture(@PathVariable("file") Integer file);

    @Operation(summary = "Обновить отображаемое имя")
    @PutMapping("/nickname")
    ResponseEntity<?> updateNickname(@RequestHeader("nickname") String nickname);

    @Operation(summary = "Получить текущие настройки")
    @GetMapping("/settings")
    ResponseEntity<UserSettingsDTO> getSettings();

    @Operation(summary = "Обновить токен Firebase")
    @PutMapping("/firebase")
    ResponseEntity<?> updateFirebaseToken(@RequestParam String token);

    @Operation(summary = "Получить новости")
    @GetMapping("/feed")
    ResponseEntity<List<ResponsePostDTO>> getFeed(@RequestParam("page") Integer page);
}
