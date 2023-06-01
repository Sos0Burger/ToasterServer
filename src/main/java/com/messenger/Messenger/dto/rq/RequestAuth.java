package com.messenger.Messenger.dto.rq;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RequestAuth {
    @NotNull
    private String email;
    @NotNull
    private String hash;
}
