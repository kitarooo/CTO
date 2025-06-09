package com.example.cto.error.exceptions;

import lombok.Getter;

@Getter
public class InsufficientFundsException extends RuntimeException {

    private final Long accountId;

    public InsufficientFundsException(Long accountId) {
        this.accountId = accountId;
    }

}
