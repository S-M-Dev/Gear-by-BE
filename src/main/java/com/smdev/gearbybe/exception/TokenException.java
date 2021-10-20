package com.smdev.gearbybe.exception;

import lombok.Getter;

public class TokenException extends RuntimeException{

    @Getter
    private final String reason;

    public TokenException(String reason) {
        this.reason = reason;
    }
}
