package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler({SystemBaseNonHandleException.class, RuntimeException.class})
    public ResponseEntity<ApiErrorResponse> handleRunTimeException(Exception e) {
        if (e instanceof SystemBaseNonHandleException customException) {
            return new ResponseEntity<>(
                    new ApiErrorResponse(customException.getErrorCode(), customException.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

        SystemBaseNonHandleException systemException =
                new SystemBaseNonHandleException(e.getMessage(), "RUNTIME_EXCEPTION");

        return new ResponseEntity<>(
                new ApiErrorResponse(systemException.getErrorCode(), systemException.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}