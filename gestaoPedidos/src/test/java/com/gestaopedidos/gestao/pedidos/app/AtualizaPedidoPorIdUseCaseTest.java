package com.gestaopedidos.gestao.pedidos.app;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.doThrow;

import com.gestaopedidos.gestao.pedidos.domain.dto.request.UpdatePedidoDTO;
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

    private UpdatePedidoDTO dto;

    @BeforeEach
    public void setUp() {
        dto = new UpdatePedidoDTO(StatusEnum.EM_CURSO,"01508001",-15d,30d);
    }

    @Test
    void testAtualizaPedidoPorId() throws SystemBaseHandleException {
        long id = 1L;

        atualizaPedidoPorIdUseCase.atualizaPedidoPorId(id, dto);

        verify(pedidoGateway, times(1)).atualizarStatusPedidoPorId(id, dto);
    }

    @Test
    void testAtualizaPedidoPorIdThrowsException() throws SystemBaseHandleException {
        long id = 1L;
        doThrow(new SystemBaseHandleException("Error")).when(pedidoGateway).atualizarStatusPedidoPorId(id, dto);

        try {
            atualizaPedidoPorIdUseCase.atualizaPedidoPorId(id, dto);
        } catch (SystemBaseHandleException e) {
            // Exception is expected
        }

        verify(pedidoGateway, times(1)).atualizarStatusPedidoPorId(id, dto);
    }
}
