package com.sosoburger.toaster.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ExpiredException extends RuntimeException{
    private String message;
}
