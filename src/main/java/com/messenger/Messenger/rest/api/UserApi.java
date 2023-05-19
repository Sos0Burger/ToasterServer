package com.messenger.Messenger.rest.api;

import com.messenger.Messenger.dto.RequestUserDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/users")
public interface UserApi {
    @Operation(summary = "Создание студента")
    @PostMapping
    ResponseEntity<?> create(@Validated @RequestBody RequestUserDTO requestUserDTO);
}
