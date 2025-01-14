package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.exception;

import lombok.Getter;

@Getter
public class SystemBaseHandleException extends Exception {

    public SystemBaseHandleException(String message) {
        super(message);
    }
    @Override
    public Throwable fillInStackTrace(){
        return this;
    }
}