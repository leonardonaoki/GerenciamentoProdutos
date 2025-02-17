package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.app.*;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.ProdutoDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.ResponseDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.request.InsertAndUpdateProdutoDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.responses.ListProdutosResponseDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.entity.CsvFile;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.exception.SystemBaseHandleException;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.infrastructure.controller.ProdutosController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    void deveRetornarUmBadRequestSeArquivoForVazio() throws SystemBaseHandleException {
        fileTeste = new MockMultipartFile("upload.csv", new byte[]{});

        ResponseEntity<?> response = produtosController.importaProdutosCSV(fileTeste);
        ResponseDTO mensagem = (ResponseDTO) response.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Arquivo vazio!", mensagem.Message());
    }

    @Test
    void deveRetornarUmBadRequestSeArquivoNaoForCSV() throws SystemBaseHandleException {
        fileTeste = new MockMultipartFile("upload.xml", new byte[]{1});
        ResponseEntity<?> response = produtosController.importaProdutosCSV(fileTeste);
        ResponseDTO mensagem = (ResponseDTO) response.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Arquivo inválido!", mensagem.Message());
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
        assertEquals(ProdutosController.NOME_ARQUIVO_PADRAO, capturedCsvFile.getNomeArquivo());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void listarProdutosDeveExecutarListarProdutosUseCase() throws Exception {
        List<ProdutoDTO> listaProdutosDTO = new ArrayList<>();
        listaProdutosDTO.add(new ProdutoDTO(1,"DescTeste", new BigDecimal("200.0"), 300));
        Page<ProdutoDTO> page = new PageImpl<>(listaProdutosDTO);
        ListProdutosResponseDTO responseDTO = new ListProdutosResponseDTO(page);
        when(listarProdutosUseCase.listarProdutos(0, 10)).thenReturn(responseDTO);

        mockMvc.perform(get("/produtos")
                        .param("_offset", "0")
                        .param("_limit", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.produtos").isArray());
        verify(listarProdutosUseCase, times(1)).listarProdutos(0, 10);
    }

    @Test
    void criarProdutoDeveExecutarCriarProdutoUseCase() throws Exception {
        InsertAndUpdateProdutoDTO dto = new InsertAndUpdateProdutoDTO("DescTeste", new BigDecimal("200.0"), 300);
        ProdutoDTO produtoCriado = new ProdutoDTO(1, "DescTeste", new BigDecimal("200.0"), 300);
        when(criarProdutoUseCase.criarProduto(any())).thenReturn(produtoCriado);

        mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.Produto.id").value(1))
                .andExpect(jsonPath("$.Produto.descricao").value("DescTeste"));
        verify(criarProdutoUseCase, times(1)).criarProduto(any());
    }

    @Test
    void atualizarProdutoPorIdDeveExecutarAtualizaProdutoUseCase() throws Exception {
        InsertAndUpdateProdutoDTO dto = new InsertAndUpdateProdutoDTO("DescTeste", new BigDecimal("200.0"), 300);
        ProdutoDTO produtoAtualizado = new ProdutoDTO(1, "DescTeste", new BigDecimal("200.0"), 300);
        when(atualizaProdutoUseCase.atualizaProdutoPorId(eq(1L), any())).thenReturn(produtoAtualizado);

        mockMvc.perform(put("/produtos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Produto.id").value(1))
                .andExpect(jsonPath("$.Produto.descricao").value("DescTeste"));
        verify(atualizaProdutoUseCase, times(1)).atualizaProdutoPorId(eq(1L), any());
    }

    @Test
    void deletaProdutoPorIdDeveExecutarDeletaProdutoPorIdUseCase() throws Exception {
        doNothing().when(deletaProdutoPorIdUseCase).deletaProdutoPorId(1L);

        mockMvc.perform(delete("/produtos/1"))
                .andExpect(status().isOk());

        verify(deletaProdutoPorIdUseCase, times(1)).deletaProdutoPorId(1L);
    }
}
