package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.app;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.infrastructure.controller.ProdutosController;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.ResponseDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.entity.CsvFile;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.exception.SystemBaseHandleException;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.infrastructure.gateway.IFileGateway;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.infrastructure.gateway.IProdutoJobGateway;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ImportarProdutosUseCaseTest {

    private ImportarProdutosUseCase importarProdutosUseCase;
    @Mock
    private IFileGateway fileGateway;
    @Mock
    private IProdutoJobGateway produtoJobGateway;

    private CsvFile csvFile;
    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        importarProdutosUseCase = new ImportarProdutosUseCase(fileGateway,produtoJobGateway);
        csvFile = new CsvFile("dados.csv","C:\\Entrada",new byte[]{});
    }
    @Test
    void deveSalvarEImportarComSucesso() throws SystemBaseHandleException {
        doNothing().when(fileGateway).salvar(any());
        when(produtoJobGateway.execute(csvFile)).thenReturn(new ResponseDTO(200,""));
        ArgumentCaptor<CsvFile> captor = ArgumentCaptor.forClass(CsvFile.class);

        ResponseDTO response = importarProdutosUseCase.salvar(csvFile);

        verify(fileGateway, times(1)).salvar(captor.capture());
        verify(produtoJobGateway, times(1)).execute(captor.capture());

        CsvFile capturedCsvFile = captor.getValue();
        assertNotNull(capturedCsvFile);
        assertEquals(ProdutosController.NOME_ARQUIVO_PADRAO, capturedCsvFile.getNomeArquivo());
        assertEquals(HttpStatus.OK, HttpStatus.valueOf(response.HttpStatusCode()));
    }
}

