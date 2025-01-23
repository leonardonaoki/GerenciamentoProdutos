package com.gestaopedidos.gestao.pedidos.infrastructure.controller;

import com.gestaopedidos.gestao.pedidos.app.*;
import com.gestaopedidos.gestao.pedidos.domain.dto.PedidoDTO;
import com.gestaopedidos.gestao.pedidos.domain.dto.ResponseDTO;
import com.gestaopedidos.gestao.pedidos.domain.dto.request.InsertAndUpdatePedidoDTO;
import com.gestaopedidos.gestao.pedidos.domain.dto.responses.ListPedidosResponseDTO;
import com.gestaopedidos.gestao.pedidos.domain.dto.responses.PedidosAndMessagesResponseDTO;
import com.gestaopedidos.gestao.pedidos.domain.enums.StatusEnum;
import com.gestaopedidos.gestao.pedidos.exception.SystemBaseHandleException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@RestController
//@RequestMapping("/pedidos")
//@RequiredArgsConstructor
//public class PedidoController {
//
//    private final MessageProducer messageProducer;
//
//    @GetMapping()
//    public String sendMessage() {
//        messageProducer.atualizaEstoque();  // Envia a mensagem para o RabbitMQ
//        return "Message sent to RabbitMQ!";
//    }
//}

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
@Slf4j
public class PedidosController {
    private final ListarPedidosUseCase listarPedidosUseCase;
    private final ListarPedidoPorIdUseCase listarPedidoPorIdUseCase;
    private final CriarPedidoUseCase criarPedidoUseCase;
    private final AtualizaPedidoPorIdUseCase atualizaPedidoUseCase;

    @GetMapping()
    @Operation(
            operationId = "ListarProdutos",
            summary = "Lista os produtos existentes de forma paginada",
            description = "Esse endpoint lista os produtos existentes de forma paginada",
            tags = "CatalogoProdutos",
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
    public ResponseEntity<ListPedidosResponseDTO> listarProdutos(
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
            operationId = "ListarProdutosPorId",
            summary = "Lista o produto por ID",
            description = "Esse endpoint lista o produto por ID",
            tags = "CatalogoProdutos",
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
    public ResponseEntity<PedidosAndMessagesResponseDTO> listarProdutosPorId(@PathVariable(value = "id", required = true) int id) {
        try {
            PedidoDTO produtoEncontrado = listarPedidoPorIdUseCase.listarPedidoPorId(id);
            return ResponseEntity.ok().body(
                    new PedidosAndMessagesResponseDTO(
                            produtoEncontrado,
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
            operationId = "CriarProduto",
            summary = "Cria um produto",
            description = "Esse endpoint cria um produto",
            tags = "CatalogoProdutos",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Created",
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
    public ResponseEntity<PedidosAndMessagesResponseDTO> criarProduto(
            @Valid @RequestBody(required = true) InsertAndUpdatePedidoDTO dto
    ){
        try{
            PedidoDTO produtoInserido = criarPedidoUseCase.criarPedido(dto);
            return ResponseEntity.ok().body(
                    new PedidosAndMessagesResponseDTO(
                            produtoInserido,
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
            operationId = "AtualizarProduto",
            summary = "Atualiza um produto",
            description = "Esse endpoint atualiza um produto",
            tags = "CatalogoProdutos",
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
    public ResponseEntity<PedidosAndMessagesResponseDTO> atualizarProdutoPorId(
            @PathVariable(value = "id", required = true) long id, StatusEnum statusEnum
    ) {
        try {
            PedidoDTO produtoAtualizado = atualizaPedidoUseCase.atualizaPedidoPorId(id, statusEnum);
            return ResponseEntity.ok().body(
                    new PedidosAndMessagesResponseDTO(
                            produtoAtualizado,
                            HttpStatus.OK.value(),
                            "Produto atualizado com sucesso!")
            );
        } catch (SystemBaseHandleException s) {
            return ResponseEntity.internalServerError().body(
                    new PedidosAndMessagesResponseDTO(
                            null,
                            HttpStatus.BAD_REQUEST.value(),
                            s.getMessage()));
        }
    }
}