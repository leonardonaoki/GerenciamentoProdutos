package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.app.AtualizaListaProdutoUseCase;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.consumer.AtualizacaoProdutosDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
public class ConsumerConfig {

    private final AtualizaListaProdutoUseCase atualizaListaProdutoUseCase;
    @Bean
    public Consumer<String> atualizaEstoque() {
        return message -> {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                AtualizacaoProdutosDTO dto = objectMapper.readValue(message, AtualizacaoProdutosDTO.class);
                atualizaListaProdutoUseCase.atualizaListaProdutos(dto);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }
}