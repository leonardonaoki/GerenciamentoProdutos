package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.rabbitmq;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class ConsumerConfig {

    @Bean
    public Consumer<String> atualizaEstoque() {
        return message -> {
            System.out.println("Mensagem recebida: " + message);
            // Aqui você pode adicionar lógica para processar a mensagem
        };
    }
}