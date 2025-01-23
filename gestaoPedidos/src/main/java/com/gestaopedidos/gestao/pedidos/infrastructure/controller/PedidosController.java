package com.gestaopedidos.gestao.pedidos.infrastructure.controller;

import com.gestaopedidos.gestao.pedidos.app.*;
import com.gestaopedidos.gestao.pedidos.domain.dto.PedidoDTO;
import com.gestaopedidos.gestao.pedidos.domain.dto.ResponseDTO;
import com.gestaopedidos.gestao.pedidos.domain.dto.request.UpdatePedidoDTO;
import com.gestaopedidos.gestao.pedidos.domain.dto.request.InsertPedidoDTO;
import com.gestaopedidos.gestao.pedidos.domain.dto.responses.ListPedidosResponseDTO;
import com.gestaopedidos.gestao.pedidos.domain.dto.responses.PedidosAndMessagesResponseDTO;
import com.gestaopedidos.gestao.pedidos.exception.SystemBaseHandleException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidosController {
    private final ListarPedidosUseCase listarPedidosUseCase;
    private final ListarPedidoPorIdUseCase listarPedidoPorIdUseCase;
    private final CriarPedidoUseCase criarPedidoUseCase;
    private final AtualizaPedidoPorIdUseCase atualizaPedidoUseCase;

    @GetMapping()
    @Operation(
            operationId = "ListarPedidos",
            summary = "Lista os pedidos existentes de forma paginada",
            description = "Esse endpoint lista os pedidos existentes de forma paginada",
            tags = "GestaoPedidos",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ListPedidosResponseDTO.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ResponseDTO.class)
                                    )
                            }
                    )
            }
    )
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
    @Operation(
            operationId = "ListarPedidosPorId",
            summary = "Lista o pedido por ID",
            description = "Esse endpoint lista o pedido por ID",
            tags = "GestaoPedidos",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = PedidosAndMessagesResponseDTO.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = PedidosAndMessagesResponseDTO.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = PedidosAndMessagesResponseDTO.class)
                                    )
                            }
                    )
            }
    )
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
    @Operation(
            operationId = "CriarPedido",
            summary = "Cria um pedido",
            description = "Esse endpoint cria um pedido",
            tags = "GestaoPedidos",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = PedidosAndMessagesResponseDTO.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = PedidosAndMessagesResponseDTO.class)
                                    )
                            }
                    )
            }
    )
    public ResponseEntity<PedidosAndMessagesResponseDTO> criarPedido(
            @Valid @RequestBody(required = true) InsertPedidoDTO dto
    ){
        try{
            PedidoDTO pedidoInserido = criarPedidoUseCase.criarPedido(dto);
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
    @Operation(
            operationId = "AtualizarPedido",
            summary = "Atualiza um pedido",
            description = "Esse endpoint atualiza um pedido",
            tags = "GestaoPedidos",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = PedidosAndMessagesResponseDTO.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = PedidosAndMessagesResponseDTO.class)
                                    )
                            }
                    )
            }
    )
    public ResponseEntity<PedidosAndMessagesResponseDTO> atualizarPedidoPorId(
            @PathVariable(value = "id", required = true) long id, @Valid @RequestBody(required = true) UpdatePedidoDTO dto
    ) {
        try {
            atualizaPedidoUseCase.atualizaPedidoPorId(id, dto);
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