package com.gestaoclientes.clientes.domain.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gerenciamentoclientes.clientes.app.*;
import com.gerenciamentoclientes.clientes.domain.dto.ClienteDTO;
import com.gerenciamentoclientes.clientes.domain.dto.request.InsertAndUpdateClienteDTO;
import com.gerenciamentoclientes.clientes.domain.dto.response.ListaClientesResponseDTO;
import com.gerenciamentoclientes.clientes.infrastructure.controller.ClienteController;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

class ClienteControllerTest {

    @InjectMocks
    private ClienteController clienteController;

    @Mock
    private ListarClientesUseCase listarClientesUseCase;

    @Mock
    private ListarClientePorIdUseCase listarClientePorIdUseCase;

    @Mock
    private CriarClienteUseCase criarClienteUseCase;

    @Mock
    private AtualizaClientePorIdUseCase atualizaClientePorIdUseCase;

    @Mock
    private DeletaClientePorIdUseCase deletaClientePorIdUseCase;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(clienteController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void listarClientesDeveExecutarListarClientesUseCase() throws Exception {
        List<ClienteDTO> listaClientesDTO = new ArrayList<>();
        listaClientesDTO.add(new ClienteDTO(1L,"NomeCliente", "email@teste.com"));
        Page<ClienteDTO> page = new PageImpl<>(listaClientesDTO);

        ListaClientesResponseDTO responseDTO = new ListaClientesResponseDTO(page);
        when(listarClientesUseCase.listarClientes(0, 10)).thenReturn(responseDTO);

        mockMvc.perform(get("/clientes")
                .param("_offset", "0")
                .param("_limit", "10"));

        verify(listarClientesUseCase, times(1)).listarClientes(0, 10);
    }

    @Test
    void listarClientesPorIdDeveExecutarListarClientePorIDUseCase() throws Exception {
        ClienteDTO clienteDTO = new ClienteDTO(1L, "NomeCliente", "email@teste.com");
        when(listarClientePorIdUseCase.listarClientePorId(1)).thenReturn(clienteDTO);

        mockMvc.perform(get("/clientes/1"));

        verify(listarClientePorIdUseCase, times(1)).listarClientePorId(1);
    }

    @Test
    void criarClienteDeveExecutarCriarClienteUseCase() throws Exception {
        InsertAndUpdateClienteDTO dto = new InsertAndUpdateClienteDTO("NomeCliente", "email@teste.com");
        ClienteDTO clienteCriado = new ClienteDTO(1L, "NomeCliente", "email@teste.com");
        when(criarClienteUseCase.criarCliente(any())).thenReturn(clienteCriado);

        mockMvc.perform(post("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));

        verify(criarClienteUseCase, times(1)).criarCliente(any());
    }

    @Test
    void atualizarClientePorIdDeveExecutarAtualizaClienteUseCase() throws Exception {
        InsertAndUpdateClienteDTO dto = new InsertAndUpdateClienteDTO("NomeCliente", "email@teste.com");
        ClienteDTO clienteAtualizado = new ClienteDTO(1L, "NomeCliente", "email@teste.com");
        when(atualizaClientePorIdUseCase.atualizaClientePorId(eq(1L), any())).thenReturn(clienteAtualizado);

        mockMvc.perform(put("/clientes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));

        verify(atualizaClientePorIdUseCase, times(1)).atualizaClientePorId(eq(1L), any());
    }

    @Test
    void deletaClientePorIdDeveExecutarDeletaClientePorIdUseCase() throws Exception {
        doNothing().when(deletaClientePorIdUseCase).deletaClientePorId(1L);

        mockMvc.perform(delete("/clientes/1"));

        verify(deletaClientePorIdUseCase, times(1)).deletaClientePorId(1L);
    }
}
