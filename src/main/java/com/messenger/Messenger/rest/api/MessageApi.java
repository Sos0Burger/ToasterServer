package com.messenger.Messenger.rest.api;

import com.messenger.Messenger.dto.rq.RequestMessageDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/message")
public interface MessageApi {
    @Operation(summary = "Создание сообщения")
    @PostMapping
    ResponseEntity<?> create(@Validated @RequestBody RequestMessageDTO message);
    @Operation(summary = "Получить все сообщения")
    @GetMapping
    ResponseEntity<?> getAll();
    @Operation(summary = "Получить диалог")
    @GetMapping("/{userid}/{companionid}")
    ResponseEntity<?> getDialog(@PathVariable("userid") Integer userid, @PathVariable("companionid") Integer companionid);
}
