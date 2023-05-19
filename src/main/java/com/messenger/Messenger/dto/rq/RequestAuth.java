package com.messenger.Messenger.dto.rq;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RequestAuth {
    private String email;
    private String password;
}
