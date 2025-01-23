package com.gestaopedidos.gestao.pedidos.rabbitmq;

import com.gestaopedidos.gestao.pedidos.domain.dto.AtualizaEstoqueRabbitDTO;
import com.gestaopedidos.gestao.pedidos.domain.dto.request.ProdutoDTO;
import com.gestaopedidos.gestao.pedidos.domain.enums.AcaoEstoqueEnum;
import com.gestaopedidos.gestao.pedidos.exception.SystemBaseNonHandleException;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EstoqueProducer implements IEstoque {
    private static final String ATUALIZA_ESTOQUE = "atualizaEstoque-out-0";

    private final StreamBridge streamBridge;

    @Override
    public void atualizaEstoque(List<ProdutoDTO> listaProdutos , AcaoEstoqueEnum acao) {
        try{
            streamBridge.send(ATUALIZA_ESTOQUE, new AtualizaEstoqueRabbitDTO(listaProdutos,acao));
        }
        catch(Exception e){
            throw new SystemBaseNonHandleException("Erro ao mandar a mensagem para o microserviço de catálogo de produtos",
                    HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }
    }
}
