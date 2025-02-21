package com.gestaopedidos.gestao.pedidos.app;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.doThrow;

import com.gestaopedidos.gestao.pedidos.domain.entity.UpdatePedidoDomain;
import com.gestaopedidos.gestao.pedidos.domain.enums.StatusEnum;
import com.gestaopedidos.gestao.pedidos.exception.SystemBaseHandleException;
import com.gestaopedidos.gestao.pedidos.infrastructure.gateway.IPedidoGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AtualizaPedidoPorIdUseCaseTest {

    @Mock
    private IPedidoGateway pedidoGateway;

    @InjectMocks
    private AtualizaPedidoPorIdUseCase atualizaPedidoPorIdUseCase;

    private UpdatePedidoDomain domain;

    @BeforeEach
    public void setUp() {
        domain = new UpdatePedidoDomain(StatusEnum.CONFIRMADO,"01508001",
                -15d,30d);
    }

    @Test
    void testAtualizaPedidoPorId() throws SystemBaseHandleException {
        long id = 1L;

        atualizaPedidoPorIdUseCase.atualizaPedidoPorId(id, domain);

        verify(pedidoGateway, times(1)).atualizarStatusPedidoPorId(id, domain);
    }

    @Test
    void testAtualizaPedidoPorIdThrowsException() throws SystemBaseHandleException {
        long id = 1L;
        doThrow(new SystemBaseHandleException("Error")).when(pedidoGateway).atualizarStatusPedidoPorId(id, domain);

        assertThrows(SystemBaseHandleException.class, () -> atualizaPedidoPorIdUseCase.atualizaPedidoPorId(id, domain));

        verify(pedidoGateway, times(1)).atualizarStatusPedidoPorId(id, domain);
    }
}
