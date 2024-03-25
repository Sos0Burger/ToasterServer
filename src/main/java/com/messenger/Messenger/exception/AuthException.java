package com.messenger.Messenger.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AuthException extends RuntimeException{
    private String message;
}
