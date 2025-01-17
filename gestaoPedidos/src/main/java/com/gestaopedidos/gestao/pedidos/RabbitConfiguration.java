package com.gestaopedidos.gestao.pedidos;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {

    @Value("${queue.atualiza-estoque.name}")
    private String atualizaEstoqueQueueName;

    @Value("${queue.atualiza-estoque.exchange.name}")
    private String atualizaEstoqueExchangeName;

    @Value("${queue.atualiza-estoque-dlx.key}")
    private String atualizaEstoqueDlxKey;

    @Bean("atualizaEstoqueQueue")
    Queue atualizaEstoqueQueue() {
        return new Queue(atualizaEstoqueQueueName, true);
    }

    @Bean("atualizaEstoqueDlx")
    TopicExchange atualizaEstoqueDlx() {
        return new TopicExchange(atualizaEstoqueExchangeName);
    }

    @Bean
    Binding atualizaEstoqueBinding(
            @Qualifier("atualizaEstoqueQueue") Queue atualizaEstoqueQueue,
            @Qualifier("atualizaEstoqueDlx") TopicExchange atualizaEstoqueDlx) {
        return BindingBuilder.bind(atualizaEstoqueQueue).to(atualizaEstoqueDlx).with(atualizaEstoqueDlxKey);
    }
}
