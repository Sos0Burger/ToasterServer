package com.sosoburger.toaster.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UploadException extends RuntimeException{
    private String message;
}
