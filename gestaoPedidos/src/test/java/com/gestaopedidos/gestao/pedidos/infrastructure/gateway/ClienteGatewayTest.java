package com.gestaopedidos.gestao.pedidos.infrastructure.gateway;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.gestaopedidos.gestao.pedidos.infrastructure.gateway.json.FindByClienteIdResponseDTO;
import com.gestaopedidos.gestao.pedidos.infrastructure.gateway.json.FindByIdClienteDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

class ClienteGatewayTest {
    @Mock
    private RestTemplate restTemplate;

    @Mock
    private Environment env;

    @InjectMocks
    private ClienteGateway clienteGateway;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(env.getProperty("api-cliente-base-path")).thenReturn("http://localhost:8080/api/cliente");
    }

    @Test
    void testFindById() {
        long id = 1L;
        String url = null + "/" + id;
        FindByIdClienteDTO clienteDTO = new FindByIdClienteDTO(1L,"nome teste","email teste");
        FindByClienteIdResponseDTO response = new FindByClienteIdResponseDTO(clienteDTO,200,"Sucesso");
        when(restTemplate.getForObject(url, FindByClienteIdResponseDTO.class)).thenReturn(response);

        FindByClienteIdResponseDTO result = clienteGateway.findById(id);

        assertNotNull(result);
        assertEquals(response, result);
        verify(restTemplate, times(1)).getForObject(url, FindByClienteIdResponseDTO.class);
    }
}
