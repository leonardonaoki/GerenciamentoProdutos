package com.gestaoclientes.clientes.exception;

import com.gerenciamentoclientes.clientes.exception.SystemBaseHandleException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SystemBaseHandleExceptionTest {

    @Test
    void deveCriarExcecaoComMensagemCorreta() {
        // Arrange
        String mensagemEsperada = "Erro no sistema";

        // Act
        SystemBaseHandleException exception = new SystemBaseHandleException(mensagemEsperada);

        // Assert
        assertEquals(mensagemEsperada, exception.getMessage());
    }

    @Test
    void deveRetornarEstaQuandoFillInStackTraceForChamado() {
        // Arrange
        SystemBaseHandleException exception = new SystemBaseHandleException("Erro no sistema");

        // Act
        Throwable resultado = exception.fillInStackTrace();

        // Assert
        assertSame(exception, resultado, "O método fillInStackTrace deve retornar a própria exceção.");
    }

    @Test
    void deveSerInstanciadaCorretamenteQuandoMensagemForNula() {
        // Arrange
        String mensagemEsperada = null;

        // Act
        SystemBaseHandleException exception = new SystemBaseHandleException(mensagemEsperada);

        // Assert
        assertNull(exception.getMessage());
    }
}
