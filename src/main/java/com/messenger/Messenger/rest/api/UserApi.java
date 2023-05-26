package com.messenger.Messenger.rest.api;

import com.messenger.Messenger.dto.rq.RequestAuth;
import com.messenger.Messenger.dto.rq.RequestUserDTO;
import com.messenger.Messenger.dto.rs.FriendDTO;
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
    @Operation(summary = "Создание пользователя")
    @PostMapping
    ResponseEntity<?> create(@Validated @RequestBody RequestUserDTO requestUserDTO);

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

    @Operation(summary = "Авторизация")
    @GetMapping("/auth")
    ResponseEntity<ExceptionMessage> auth(@RequestHeader(value = "email")String email, @RequestHeader(value = "hash") String hash);

    @Operation(summary = "Получение данных группы пользователей")
    @GetMapping("/ids")
    ResponseEntity<?> getByIds(@RequestParam List<Integer> ids);

    @Operation(summary = "Отправка заявки в друзья")
    @PostMapping("/friend-request/{senderid}/{receiverid}")
    ResponseEntity<?> sendFriendRequest(@PathVariable(name = "senderid") Integer senderid, @PathVariable(name = "receiverid") Integer receiverid);

    @Operation(summary = "Принять запрос в друзья")
    @PostMapping("/friends/{receiverid}/{senderid}")
    ResponseEntity<?> acceptFriendRequest(@PathVariable(name = "receiverid") Integer receiverid, @PathVariable(name = "senderid") Integer senderid);

    @Operation(summary = "Список друзей")
    @GetMapping("/{id}/friends")
    ResponseEntity<?> getFriends(@PathVariable("id") Integer id);


}
