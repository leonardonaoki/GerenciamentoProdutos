package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.controller;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.app.ImportarProdutoUseCase;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.controller.ProdutoController;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.ResponseDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.entity.CsvFile;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.exception.SystemBaseHandleException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProdutoControllerTests {

    @InjectMocks
    private ProdutoController produtoController;
    @Mock
    private ImportarProdutoUseCase importarProdutoUseCase;
    private MultipartFile fileTeste;
    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void deveRetornarUmBadRequestSeArquivoForVazio() throws SystemBaseHandleException {
        fileTeste = new MockMultipartFile("upload.csv",new byte[]{});

        ResponseEntity<?> response = produtoController.importaProdutosCSV(fileTeste);
        ResponseDTO mensagem = (ResponseDTO) response.getBody();

        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        assertEquals("Arquivo vazio!",mensagem.Message());
    }
    @Test
    void deveRetornarUmBadRequestSeArquivoNaoForCSV() throws SystemBaseHandleException {
        fileTeste = new MockMultipartFile("upload.xml",new byte[]{1});
        ResponseEntity<?> response = produtoController.importaProdutosCSV(fileTeste);
        ResponseDTO mensagem = (ResponseDTO) response.getBody();

        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        assertEquals("Arquivo inválido!",mensagem.Message());
    }

    @Test
    void deveExecutarOUseCaseCorretamente() throws SystemBaseHandleException {
        fileTeste = new MockMultipartFile("upload.csv", "upload.csv", "multipart/form-data", new byte[]{1});

        ArgumentCaptor<CsvFile> captor = ArgumentCaptor.forClass(CsvFile.class);
        when(importarProdutoUseCase.salvar(any())).thenReturn(new ResponseDTO(HttpStatus.OK.value(), ""));

        ResponseEntity<?> response = produtoController.importaProdutosCSV(fileTeste);

        verify(importarProdutoUseCase, times(1)).salvar(captor.capture());

        CsvFile capturedCsvFile = captor.getValue();
        assertNotNull(capturedCsvFile);
        assertEquals(ProdutoController.NOME_ARQUIVO_PADRAO, capturedCsvFile.getNomeArquivo());  // Verifique se o nome do arquivo está correto
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    void deveExibirUmErroAoNaoConseguirReceberOsBytes() throws IOException, SystemBaseHandleException {
        MultipartFile mockFileTeste = mock(MultipartFile.class);

        when(mockFileTeste.isEmpty()).thenReturn(false);
        when(mockFileTeste.getOriginalFilename()).thenReturn("dados.csv");
        doThrow(new IOException()).when(mockFileTeste).getBytes();

        ResponseEntity<?> response = produtoController.importaProdutosCSV(mockFileTeste);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCode().value());
    }
}
