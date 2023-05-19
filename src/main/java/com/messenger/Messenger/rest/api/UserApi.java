package com.messenger.Messenger.rest.api;

import com.messenger.Messenger.dto.rq.RequestAuth;
import com.messenger.Messenger.dto.rq.RequestUserDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/users")
public interface UserApi {
    @Operation(summary = "Создание пользователя")
    @PostMapping
    ResponseEntity<?> create(@Validated @RequestBody RequestUserDTO requestUserDTO);

    @Operation(summary = "Получение всех пользователей")
    @GetMapping
    ResponseEntity<?> getAll();

    @Operation(summary = "Удаление пользователя по id")
    @DeleteMapping("{id}")
    ResponseEntity<?> delete(@PathVariable(name = "id") Integer id);
    @Operation(summary = "Авторизация")
    @GetMapping("/auth")
    ResponseEntity<?> auth(@RequestBody RequestAuth auth);
    @Operation(summary = "Получение данных группы пользователей")
    @GetMapping("/ids")
    ResponseEntity<?> getByIds(@RequestBody List<Integer> ids);

    @Operation(summary = "Отправка заявки в друзья")
    @PostMapping("/{senderid}/{receiverid}")
    ResponseEntity<?> sendFriendRequest(@PathVariable(name = "senderid")Integer senderid, @PathVariable(name = "receiverid")Integer receiverid);

}
