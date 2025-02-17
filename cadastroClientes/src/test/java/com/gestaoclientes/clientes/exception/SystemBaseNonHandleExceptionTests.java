package com.gestaoclientes.clientes.exception;


import com.gerenciamentoclientes.clientes.exception.ApiErrorResponse;
import com.gerenciamentoclientes.clientes.exception.CustomExceptionHandler;
import com.gerenciamentoclientes.clientes.exception.SystemBaseNonHandleException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SystemBaseNonHandleExceptionTests {

    @Test
    void deveCriarUmaExcecaoComMensagemEErro(){
        String expectedMessage = "Test error message";
        String expectedErrorCode = "ERR001";

        SystemBaseNonHandleException exception = new SystemBaseNonHandleException(expectedMessage, expectedErrorCode);

        Assertions.assertThat(exception).isInstanceOf(SystemBaseNonHandleException.class);
        Assertions.assertThat(exception.getMessage()).isEqualTo(expectedMessage);
        Assertions.assertThat(exception.getErrorCode()).isEqualTo(expectedErrorCode);
    }
    @Test
    void deveTratarSystemBaseNonHandleException() {
        // Arrange
        CustomExceptionHandler handler = new CustomExceptionHandler();
        SystemBaseNonHandleException customException = new SystemBaseNonHandleException("Exceção customizada", "ERR001");

        // Act
        ResponseEntity<ApiErrorResponse> response = handler.handleRunTimeException(customException);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCode().value()); // Verifica status 500
        assertEquals("ERR001", response.getBody().getErrorCode()); // Verifica o código de erro
        assertEquals("Exceção customizada", response.getBody().getMessage()); // Verifica a mensagem
    }
}