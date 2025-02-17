package com.gestaopedidos.gestao.pedidos.infrastructure.controller;

import com.gestaopedidos.gestao.pedidos.app.*;
import com.gestaopedidos.gestao.pedidos.domain.dto.PedidoDTO;
import com.gestaopedidos.gestao.pedidos.domain.dto.request.UpdatePedidoDTO;
import com.gestaopedidos.gestao.pedidos.domain.dto.request.InsertPedidoDTO;
import com.gestaopedidos.gestao.pedidos.domain.dto.responses.ListPedidosResponseDTO;
import com.gestaopedidos.gestao.pedidos.domain.dto.responses.PedidosAndMessagesResponseDTO;
import com.gestaopedidos.gestao.pedidos.domain.entity.InsertPedidoDomain;
import com.gestaopedidos.gestao.pedidos.domain.entity.UpdatePedidoDomain;
import com.gestaopedidos.gestao.pedidos.domain.mapper.IPedidoMapper;
import com.gestaopedidos.gestao.pedidos.exception.SystemBaseHandleException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidosController implements IControllerDocumentation{
    private final ListarPedidosUseCase listarPedidosUseCase;
    private final ListarPedidoPorIdUseCase listarPedidoPorIdUseCase;
    private final CriarPedidoUseCase criarPedidoUseCase;
    private final AtualizaPedidoPorIdUseCase atualizaPedidoUseCase;
    private final IPedidoMapper pedidoMapper;
    @GetMapping()
    @Override
    public ResponseEntity<ListPedidosResponseDTO> listarPedidos(
            @RequestParam(value = "_offset", required = false, defaultValue = "0") int offset,
            @RequestParam(value = "_limit", required = false, defaultValue = "10") int limit) {
        if (offset < 0) {
            offset = 0;
        }
        if (limit < 0) {
            limit = 10;
        }
        return ResponseEntity.ok().body(listarPedidosUseCase.listarPedidos(offset, limit));
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<PedidosAndMessagesResponseDTO> listarPedidosPorId(@PathVariable(value = "id", required = true) int id) {
        try {
            PedidoDTO pedidoEncontrado = listarPedidoPorIdUseCase.listarPedidoPorId(id);
            return ResponseEntity.ok().body(
                    new PedidosAndMessagesResponseDTO(
                            pedidoEncontrado,
                            HttpStatus.OK.value(),
                            "Sucesso")
            );
        } catch (SystemBaseHandleException s) {
            return ResponseEntity.internalServerError().body(
                    new PedidosAndMessagesResponseDTO(
                            null,
                            HttpStatus.BAD_REQUEST.value(),
                            s.getMessage()));
        }
    }

    @PostMapping()
    @Override
    public ResponseEntity<PedidosAndMessagesResponseDTO> criarPedido(
            @Valid @RequestBody(required = true) InsertPedidoDTO dto
    ){
        InsertPedidoDomain domain = pedidoMapper.toInsertDomain(dto);
        try{
            PedidoDTO pedidoInserido = criarPedidoUseCase.criarPedido(domain);
            return ResponseEntity.ok().body(
                    new PedidosAndMessagesResponseDTO(
                            pedidoInserido,
                            HttpStatus.CREATED.value(),
                            "Sucesso")
            );
        }catch (SystemBaseHandleException s) {
            return ResponseEntity.internalServerError().body(
                    new PedidosAndMessagesResponseDTO(
                            null,
                            HttpStatus.BAD_REQUEST.value(),
                            s.getMessage()));
        }
    }

    @PatchMapping("/{id}")
    @Override
    public ResponseEntity<PedidosAndMessagesResponseDTO> atualizarPedidoPorId(
            @PathVariable(value = "id", required = true) long id, @Valid @RequestBody(required = true) UpdatePedidoDTO dto
    ) {
        UpdatePedidoDomain domain = pedidoMapper.toUpdateDomain(dto);
        try {
            atualizaPedidoUseCase.atualizaPedidoPorId(id, domain);
            return ResponseEntity.noContent().build();
        } catch (SystemBaseHandleException s) {
            return ResponseEntity.internalServerError().body(
                    new PedidosAndMessagesResponseDTO(
                            null,
                            HttpStatus.BAD_REQUEST.value(),
                            s.getMessage()));
        }
    }
}