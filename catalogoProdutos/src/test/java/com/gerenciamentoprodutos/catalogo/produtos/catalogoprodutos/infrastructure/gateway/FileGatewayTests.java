package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.infrastructure.gateway;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.entity.CsvFile;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.exception.SystemBaseHandleException;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.nio.file.Files;

class FileGatewayTests {
    @Mock
    private IFileGateway fileGateway;
    @Mock
    private CsvFile csvFile;
    @BeforeEach
    void setUp() {
        // Inicializa os mocks do Mockito
        MockitoAnnotations.openMocks(this);
        fileGateway = new FileGateway();
        csvFile = new CsvFile("dados.csv", "C://Entrada", new byte[] {});
    }
    @Test
    void deveSalvarOArquivoCorretamente() throws SystemBaseHandleException {
        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.createDirectories(any())).thenReturn(null);
            mockedFiles.when(() -> Files.write(any(), any(byte[].class))).thenReturn(null);

            fileGateway.salvar(csvFile);

            mockedFiles.verify(() -> Files.createDirectories(any()));
            mockedFiles.verify(() -> Files.write(any(), any(byte[].class)));
        }
    }
    @Test
    void deveRetornarUmErroSeArquivoNaoExistir() {
        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {

            mockedFiles.when(() -> Files.createDirectories(any())).thenReturn(null);
            mockedFiles.when(() -> Files.write(any(), any(byte[].class))).thenReturn(null);
            mockedFiles.when(() -> Files.notExists(any())).thenReturn(true);

            // Verificando se a exceção é lançada
            assertThrows(SystemBaseHandleException.class, () -> fileGateway.salvar(csvFile));
        }
    }
}
