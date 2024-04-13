package com.sosoburger.toaster.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AlreadyExistsException extends RuntimeException {
    private String  message;
}
