package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.exception;

import lombok.Getter;

@Getter
public class SystemBaseNonHandleException extends RuntimeException {

    private final String errorCode;

    public SystemBaseNonHandleException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    @Override
    public Throwable fillInStackTrace(){
        return this;
    }
}