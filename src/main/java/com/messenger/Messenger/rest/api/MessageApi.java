package com.messenger.Messenger.rest.api;

import com.messenger.Messenger.dto.rq.RequestMessageDTO;
import com.messenger.Messenger.dto.rs.ResponseMessageDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/message")
public interface MessageApi {
    @Operation(summary = "Создание сообщения")
    @PostMapping
    ResponseEntity<ResponseMessageDTO> create(@Validated @RequestBody RequestMessageDTO message);
    @Operation(summary = "Получить все сообщения")
    @GetMapping
    ResponseEntity<List<ResponseMessageDTO>> getAll();
    @Operation(summary = "Получить диалог")
    @GetMapping("/{userid}/{companionid}/{page}")
    ResponseEntity<List<ResponseMessageDTO>> getDialog(@PathVariable("userid") Integer userid, @PathVariable("companionid") Integer companionid, @PathVariable("page")Integer page);
}
