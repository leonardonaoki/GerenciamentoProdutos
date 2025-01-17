package com.gestaopedidos.gestao.pedidos;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Component
@RequiredArgsConstructor

public class MessageProducer implements ITeste{
    private static final String ATUALIZA_ESTOQUE = "atualizaEstoque-out-0";

    private final StreamBridge streamBridge;

    @Override
    public void atualizaEstoque() {
        try{
            streamBridge.send(ATUALIZA_ESTOQUE, "teste");
        }
        catch(Exception e){
            throw new RuntimeException();
        }

    }
}
