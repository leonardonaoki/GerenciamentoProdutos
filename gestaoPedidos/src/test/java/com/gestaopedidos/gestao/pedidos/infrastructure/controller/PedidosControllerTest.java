package com.gestaopedidos.gestao.pedidos.infrastructure.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestaopedidos.gestao.pedidos.app.*;
import com.gestaopedidos.gestao.pedidos.domain.dto.PedidoDTO;
import com.gestaopedidos.gestao.pedidos.domain.dto.request.InsertPedidoDTO;
import com.gestaopedidos.gestao.pedidos.domain.dto.request.ProdutoDTO;
import com.gestaopedidos.gestao.pedidos.domain.dto.request.UpdatePedidoDTO;
import com.gestaopedidos.gestao.pedidos.domain.dto.responses.ListPedidosResponseDTO;
import com.gestaopedidos.gestao.pedidos.domain.entity.UpdatePedidoDomain;
import com.gestaopedidos.gestao.pedidos.domain.enums.StatusEnum;
import com.gestaopedidos.gestao.pedidos.domain.mapper.IPedidoMapper;
import com.gestaopedidos.gestao.pedidos.exception.SystemBaseHandleException;
import com.gestaopedidos.gestao.pedidos.infrastructure.gateway.IPedidoGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class PedidosControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ListarPedidosUseCase listarPedidosUseCase;

    @Mock
    private ListarPedidoPorIdUseCase listarPedidoPorIdUseCase;

    @Mock
    private CriarPedidoUseCase criarPedidoUseCase;

    @Mock
    private AtualizaPedidoPorIdUseCase atualizaPedidoUseCase;

    @Mock
    private IPedidoMapper pedidoMapper;
    @Mock
    private IPedidoGateway pedidoGateway;
    @InjectMocks
    private PedidosController pedidosController;
    private ObjectMapper objectMapper;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(pedidosController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testListarPedidos() throws Exception {
        PedidoDTO pedidoDTO = new PedidoDTO();
        List<PedidoDTO> listaPedidoDTO = new ArrayList<>();
        listaPedidoDTO.add(pedidoDTO);
        Page<PedidoDTO> paginaPedido = new PageImpl<>(listaPedidoDTO);
        ListPedidosResponseDTO responseDTO = new ListPedidosResponseDTO(paginaPedido);
        when(listarPedidosUseCase.listarPedidos(0, 10)).thenReturn(responseDTO);

        mockMvc.perform(get("/pedidos")
                .param("_offset", "0")
                .param("_limit", "10"));

        verify(listarPedidosUseCase, times(1)).listarPedidos(0, 10);
    }
    @Test
    void listarPedidosDeveAlterarOffSetELimit() throws Exception {
        PedidoDTO pedidoDTO = new PedidoDTO();
        List<PedidoDTO> listaPedidoDTO = new ArrayList<>();
        listaPedidoDTO.add(pedidoDTO);
        Page<PedidoDTO> paginaPedido = new PageImpl<>(listaPedidoDTO);

        ListPedidosResponseDTO responseDTO = new ListPedidosResponseDTO(paginaPedido);
        when(listarPedidosUseCase.listarPedidos(0, -10)).thenReturn(responseDTO);

        mockMvc.perform(get("/pedidos")
                .param("_offset", "-1")
                .param("_limit", "-1"));

        verify(listarPedidosUseCase, times(1)).listarPedidos(0, 10);
    }
    @Test
    void testListarPedidosPorId() throws Exception {
        PedidoDTO pedidoDTO = new PedidoDTO();
        when(listarPedidoPorIdUseCase.listarPedidoPorId(1)).thenReturn(pedidoDTO);

        mockMvc.perform(get("/pedidos/1"))
                .andExpect(status().isOk());

        verify(listarPedidoPorIdUseCase, times(1)).listarPedidoPorId(1);
    }

    @Test
    void listarPedidoPorIdDeveExibirSystemBaseExceptionCorretamente() throws Exception {
        when(listarPedidoPorIdUseCase.listarPedidoPorId(1))
                .thenThrow(new SystemBaseHandleException("Erro"));

        mockMvc.perform(get("/pedidos/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("{\"Pedido\":null,\"HttpStatusCode\":400,\"Message\":\"Erro\"}")); // Verifica o corpo da resposta
    }
    @Test
    void testCriarPedido() throws Exception {
        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setIdProduto(1);
        produtoDTO.setQuantidadeDesejada(200);
        InsertPedidoDTO dto = new InsertPedidoDTO(1L, List.of(produtoDTO),"12345-678",123.456,123.456);
        PedidoDTO pedidoDTO = new PedidoDTO();
        when(criarPedidoUseCase.criarPedido(any())).thenReturn(pedidoDTO);

        mockMvc.perform(post("/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)));
        verify(criarPedidoUseCase, times(1)).criarPedido(any());
    }
    @Test
    void CriarPedidoDeveExibirSystemBaseExceptionCorretamente() throws Exception {
        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setIdProduto(1);
        produtoDTO.setQuantidadeDesejada(200);
        InsertPedidoDTO dto = new InsertPedidoDTO(1L, List.of(produtoDTO),"12345-678",123.456,123.456);
        when(criarPedidoUseCase.criarPedido(any()))
                .thenThrow(new SystemBaseHandleException("Erro"));

        mockMvc.perform(post("/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("{\"Pedido\":null,\"HttpStatusCode\":400,\"Message\":\"Erro\"}")); // Verifica o corpo da resposta
    }
    @Test
    void testAtualizarPedidoPorId() throws Exception {
        UpdatePedidoDTO dto = new UpdatePedidoDTO(StatusEnum.CONFIRMADO,"12345-678",
                12.345678,98.765432);
        UpdatePedidoDomain updatePedidoDomain = new UpdatePedidoDomain(StatusEnum.CONFIRMADO,"12345-678",
                12.345678,98.765432);
        when(pedidoMapper.toUpdateDomain(any(UpdatePedidoDTO.class))).thenReturn(updatePedidoDomain);
        doNothing().when(atualizaPedidoUseCase).atualizaPedidoPorId(any(Long.class),any(UpdatePedidoDomain.class));
        mockMvc.perform(patch("/pedidos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)));
        verify(atualizaPedidoUseCase, times(1)).atualizaPedidoPorId(any(Long.class),any(UpdatePedidoDomain.class));
    }
    @Test
    void atualizarProdutoPorIdDeveExibirSystemBaseExceptionCorretamente() throws Exception {
        UpdatePedidoDTO dto = new UpdatePedidoDTO(StatusEnum.CONFIRMADO,"12345-678",
                12.345678,98.765432);
        UpdatePedidoDomain updatePedidoDomain = new UpdatePedidoDomain(StatusEnum.CONFIRMADO,"12345-678",
                12.345678,98.765432);
        when(pedidoMapper.toUpdateDomain(any(UpdatePedidoDTO.class))).thenReturn(updatePedidoDomain);
        doThrow(new SystemBaseHandleException("Erro")).when(atualizaPedidoUseCase).atualizaPedidoPorId(anyLong(),any(UpdatePedidoDomain.class));

        mockMvc.perform(patch("/pedidos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(content().string("{\"Pedido\":null,\"HttpStatusCode\":400,\"Message\":\"Erro\"}")); // Verifica o corpo da resposta
    }
}