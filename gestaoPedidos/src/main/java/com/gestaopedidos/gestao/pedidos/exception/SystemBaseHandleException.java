package com.gestaopedidos.gestao.pedidos.exception;

import lombok.Getter;

@Getter
public class SystemBaseHandleException extends Exception {

    public SystemBaseHandleException(String message) {
        super(message);
    }
    @Override
    public synchronized Throwable fillInStackTrace(){
        return this;
    }
}