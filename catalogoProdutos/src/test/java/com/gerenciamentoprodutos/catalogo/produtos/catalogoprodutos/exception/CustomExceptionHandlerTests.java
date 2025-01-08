package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.exception;



import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.exception.ApiErrorResponse;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.exception.CustomExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CustomExceptionHandlerTests {

    @Test
    void deveEncapsularRuntimeExceptionEmSystemBaseNonHandleException() {

        CustomExceptionHandler handler = new CustomExceptionHandler();
        RuntimeException runtimeException = new RuntimeException("Erro inesperado");

        // Act
        ResponseEntity<ApiErrorResponse> response = handler.handleRunTimeException(runtimeException);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode()); // Verifica status 500
        assertEquals("RUNTIME_EXCEPTION", response.getBody().getErrorCode()); // Verifica o código de erro
        assertEquals("Erro inesperado", response.getBody().getMessage()); // Verifica a mensagem
    }
}