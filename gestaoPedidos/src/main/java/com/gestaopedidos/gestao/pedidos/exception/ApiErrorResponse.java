package com.gestaopedidos.gestao.pedidos.exception;

public class ApiErrorResponse {
    private final String errorCode;
    private final String message;

    public ApiErrorResponse(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}