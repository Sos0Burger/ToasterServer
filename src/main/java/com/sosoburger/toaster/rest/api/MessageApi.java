package com.sosoburger.toaster.rest.api;

import com.sosoburger.toaster.dto.rq.RequestMessageDTO;
import com.sosoburger.toaster.dto.rs.ResponseMessageDTO;
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
    @GetMapping("/dialog")
    ResponseEntity<List<ResponseMessageDTO>> getDialog(@RequestParam("companion") Integer companion, @RequestParam("page")Integer page);
}
