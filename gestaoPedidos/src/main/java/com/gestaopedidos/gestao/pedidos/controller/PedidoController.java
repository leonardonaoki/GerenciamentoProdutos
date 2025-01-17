package com.gestaopedidos.gestao.pedidos.controller;

import com.gestaopedidos.gestao.pedidos.MessageProducer;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;




@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final MessageProducer messageProducer;

    @GetMapping()
    public String sendMessage() {
        messageProducer.atualizaEstoque();  // Envia a mensagem para o RabbitMQ
        return "Message sent to RabbitMQ!";
    }
}