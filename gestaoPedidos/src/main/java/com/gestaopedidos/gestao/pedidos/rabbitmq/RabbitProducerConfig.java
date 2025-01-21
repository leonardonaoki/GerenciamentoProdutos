package com.gestaopedidos.gestao.pedidos.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitProducerConfig {

    @Value("${queue.atualiza-estoque.name}")
    private String atualizaEstoqueQueueName;

    @Value("${queue.atualiza-estoque.exchange.name}")
    private String atualizaEstoqueExchangeName;

    @Value("${queue.atualiza-estoque-topic.key}")
    private String atualizaEstoqueTopicKey;

    @Bean("atualizaEstoqueQueue")
    Queue atualizaEstoqueQueue() {
        return new Queue(atualizaEstoqueQueueName, true);
    }

    @Bean("atualizaEstoqueTopic")
    TopicExchange atualizaEstoqueTopic() {
        return new TopicExchange(atualizaEstoqueExchangeName);
    }

    @Bean
    Binding atualizaEstoqueBinding(
            @Qualifier("atualizaEstoqueQueue") Queue atualizaEstoqueQueue,
            @Qualifier("atualizaEstoqueTopic") TopicExchange atualizaEstoqueTopic) {
        return BindingBuilder.bind(atualizaEstoqueQueue).to(atualizaEstoqueTopic).with(atualizaEstoqueTopicKey);
    }
}
