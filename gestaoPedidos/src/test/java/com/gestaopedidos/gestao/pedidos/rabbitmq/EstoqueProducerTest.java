package com.gestaopedidos.gestao.pedidos.rabbitmq;

import com.gestaopedidos.gestao.pedidos.domain.dto.AtualizaEstoqueRabbitDTO;
import com.gestaopedidos.gestao.pedidos.domain.dto.request.ProdutoDTO;
import com.gestaopedidos.gestao.pedidos.domain.enums.AcaoEstoqueEnum;
import com.gestaopedidos.gestao.pedidos.exception.SystemBaseNonHandleException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.cloud.stream.function.StreamBridge;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

class EstoqueProducerTest {

    @Mock
    private StreamBridge streamBridge;

    @InjectMocks
    private EstoqueProducer estoqueProducer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAtualizaEstoqueSuccess() {
        List<ProdutoDTO> listaProdutos = List.of(new ProdutoDTO());
        AcaoEstoqueEnum acao = AcaoEstoqueEnum.BAIXAR_ESTOQUE;

        estoqueProducer.atualizaEstoque(listaProdutos, acao);

        verify(streamBridge).send("atualizaEstoque-out-0", new AtualizaEstoqueRabbitDTO(listaProdutos, acao));
    }

    @Test
    void testAtualizaEstoqueThrowsException() {
        List<ProdutoDTO> listaProdutos = List.of(new ProdutoDTO());
        AcaoEstoqueEnum acao = AcaoEstoqueEnum.BAIXAR_ESTOQUE;

        doThrow(new RuntimeException("Test Exception")).when(streamBridge).send("atualizaEstoque-out-0", new AtualizaEstoqueRabbitDTO(listaProdutos, acao));

        assertThrows(SystemBaseNonHandleException.class, () -> {
            estoqueProducer.atualizaEstoque(listaProdutos, acao);
        });
    }
}