package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.infrastructure.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.app.*;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.ProdutoDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.ResponseDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.request.InsertAndUpdateProdutoDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.responses.ListProdutosResponseDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.entity.CsvFile;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.mapper.IProdutoMapper;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.exception.SystemBaseHandleException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;

class ProdutosControllerTests {

    @InjectMocks
    private ProdutosController produtosController;
    @Mock
    private ImportarProdutosUseCase importarProdutosUseCase;

    @Mock
    private ListarProdutosUseCase listarProdutosUseCase;

    @Mock
    private ListarProdutoPorIdUseCase listarProdutoPorIdUseCase;

    @Mock
    private CriarProdutoUseCase criarProdutoUseCase;

    @Mock
    private AtualizaProdutoPorIdUseCase atualizaProdutoUseCase;

    @Mock
    private DeletaProdutoPorIdUseCase deletaProdutoPorIdUseCase;
    @Mock
    private IProdutoMapper produtoMapper;

    private MultipartFile fileTeste;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(produtosController).build();
        objectMapper = new ObjectMapper();
    }
    @Test
    void deveRetornarUmBadRequestSeArquivoForVazio(){
        fileTeste = new MockMultipartFile("upload.csv",new byte[]{});

        ResponseEntity<?> response = produtosController.importaProdutosCSV(fileTeste);
        ResponseDTO mensagem = (ResponseDTO) response.getBody();

        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        assertEquals("Arquivo vazio!",mensagem.Message());
    }
    @Test
    void deveRetornarUmBadRequestSeArquivoNaoForCSV(){
        fileTeste = new MockMultipartFile("upload.xml",new byte[]{1});
        ResponseEntity<?> response = produtosController.importaProdutosCSV(fileTeste);
        ResponseDTO mensagem = (ResponseDTO) response.getBody();

        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        assertEquals("Arquivo inválido!",mensagem.Message());
    }

    @Test
    void deveExecutarImportarProdutosUseCaseCorretamente() throws SystemBaseHandleException {
        fileTeste = new MockMultipartFile("upload.csv", "upload.csv", "multipart/form-data", new byte[]{1});

        ArgumentCaptor<CsvFile> captor = ArgumentCaptor.forClass(CsvFile.class);
        when(importarProdutosUseCase.salvar(any())).thenReturn(new ResponseDTO(HttpStatus.OK.value(), ""));

        ResponseEntity<?> response = produtosController.importaProdutosCSV(fileTeste);

        verify(importarProdutosUseCase, times(1)).salvar(captor.capture());

        CsvFile capturedCsvFile = captor.getValue();
        assertNotNull(capturedCsvFile);
        assertEquals(ProdutosController.NOME_ARQUIVO_PADRAO, capturedCsvFile.getNomeArquivo());  // Verifique se o nome do arquivo está correto
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    void deveExibirUmErroAoNaoConseguirReceberOsBytes() throws IOException {
        MultipartFile mockFileTeste = mock(MultipartFile.class);

        when(mockFileTeste.isEmpty()).thenReturn(false);
        when(mockFileTeste.getOriginalFilename()).thenReturn("dados.csv");
        doThrow(new IOException()).when(mockFileTeste).getBytes();

        ResponseEntity<?> response = produtosController.importaProdutosCSV(mockFileTeste);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCode().value());
    }

    @Test
    void listarProdutosDeveExecutarListarProdutosUseCase() throws Exception {
        List<ProdutoDTO> listaProdutosDTO = new ArrayList<>();
        listaProdutosDTO.add(new ProdutoDTO(1,"DescTeste",new BigDecimal(200),300));
        Page<ProdutoDTO> page = new PageImpl<>(listaProdutosDTO);
        // Mock de resposta do use case
        ListProdutosResponseDTO responseDTO = new ListProdutosResponseDTO(page);
        when(listarProdutosUseCase.listarProdutos(0, 10)).thenReturn(responseDTO);

        mockMvc.perform(get("/produtos")
                        .param("_offset", "0")
                        .param("_limit", "10"));

        verify(listarProdutosUseCase, times(1)).listarProdutos(0, 10);
    }
    @Test
    void listarProdutosDeveAlterarOffSetELimit() throws Exception {
        List<ProdutoDTO> listaProdutosDTO = new ArrayList<>();
        listaProdutosDTO.add(new ProdutoDTO(1,"DescTeste",new BigDecimal(200),300));
        Page<ProdutoDTO> page = new PageImpl<>(listaProdutosDTO);
        // Mock de resposta do use case
        ListProdutosResponseDTO responseDTO = new ListProdutosResponseDTO(page);
        when(listarProdutosUseCase.listarProdutos(0, 10)).thenReturn(responseDTO);

        mockMvc.perform(get("/produtos")
                .param("_offset", "-1")
                .param("_limit", "-1"));

        verify(listarProdutosUseCase, times(1)).listarProdutos(0, 10);
    }

    @Test
    void listarProdutosPorIdDeveExecutarListarProdutosPorIDUseCase() throws Exception {
        ProdutoDTO produtoDTO = new ProdutoDTO(1,"DescTeste",new BigDecimal(200),300);
        when(listarProdutoPorIdUseCase.listarProdutoPorId(1)).thenReturn(produtoDTO);

        mockMvc.perform(get("/produtos/1"));

        verify(listarProdutoPorIdUseCase, times(1)).listarProdutoPorId(1);
    }

    @Test
    void listarProdutosPorIdDeveExibirSystemBaseExceptionCorretamente() throws Exception {
        when(listarProdutoPorIdUseCase.listarProdutoPorId(1))
                .thenThrow(new SystemBaseHandleException("Erro"));

        mockMvc.perform(get("/produtos/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("{\"Produto\":null,\"HttpStatusCode\":400,\"Message\":\"Erro\"}")); // Verifica o corpo da resposta
    }

    @Test
    void criarProdutoDeveExecutarCriarProdutoUseCase() throws Exception {
        InsertAndUpdateProdutoDTO dto = new InsertAndUpdateProdutoDTO("DescTeste",new BigDecimal(200),300);
        ProdutoDTO produtoCriado = new ProdutoDTO(1,"DescTeste",new BigDecimal(200),300);
        when(criarProdutoUseCase.criarProduto(any())).thenReturn(produtoCriado);

        mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)));

        verify(criarProdutoUseCase, times(1)).criarProduto(any());
    }

    @Test
    void atualizarProdutoPorIdDeveExecutarAtualizaProdutoUseCase() throws Exception {
        InsertAndUpdateProdutoDTO dto = new InsertAndUpdateProdutoDTO("DescTeste",new BigDecimal(200),300);
        ProdutoDTO produtoAtualizado = new ProdutoDTO(1,"DescTeste",new BigDecimal(200),300);
        when(atualizaProdutoUseCase.atualizaProdutoPorId(eq(1L), any())).thenReturn(produtoAtualizado);

        mockMvc.perform(put("/produtos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)));

        verify(atualizaProdutoUseCase, times(1)).atualizaProdutoPorId(eq(1L), any());
    }
    @Test
    void atualizarProdutoPorIdDeveExibirSystemBaseExceptionCorretamente() throws Exception {
        InsertAndUpdateProdutoDTO dto = new InsertAndUpdateProdutoDTO("DescTeste",new BigDecimal(200),300);
        when(atualizaProdutoUseCase.atualizaProdutoPorId(eq(1L),any()))
                .thenThrow(new SystemBaseHandleException("Erro"));

        mockMvc.perform(put("/produtos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("{\"Produto\":null,\"HttpStatusCode\":400,\"Message\":\"Erro\"}")); // Verifica o corpo da resposta
    }

    @Test
    void deletaProdutoPorIdDeveExecutarDeletaProdutoPorIdUseCase() throws Exception {
        doNothing().when(deletaProdutoPorIdUseCase).deletaProdutoPorId(1L);

        mockMvc.perform(delete("/produtos/1"));

        verify(deletaProdutoPorIdUseCase, times(1)).deletaProdutoPorId(1L);
    }
    @Test
    void deletaProdutoPorIdDeveExibirSystemBaseExceptionCorretamente() throws Exception {
        doThrow(new SystemBaseHandleException("Erro")).when(deletaProdutoPorIdUseCase).deletaProdutoPorId(1L);
        mockMvc.perform(delete("/produtos/1"))
                .andExpect(content().string("{\"HttpStatusCode\":400,\"Message\":\"Erro\"}")); // Verifica o corpo da resposta
    }
    @Test
    void testImportaProdutosCSV_ErroDeSistema() throws SystemBaseHandleException {
        fileTeste = new MockMultipartFile("upload.csv", "upload.csv", "multipart/form-data", new byte[]{1});

        when(importarProdutosUseCase.salvar(any())).thenReturn(new ResponseDTO(HttpStatus.OK.value(), ""));
        when(importarProdutosUseCase.salvar(any(CsvFile.class)))
                .thenThrow(new SystemBaseHandleException("Erro no sistema"));

        ResponseEntity<ResponseDTO> response = produtosController.importaProdutosCSV(fileTeste);

        assertEquals(500, response.getStatusCode().value());
    }
}
