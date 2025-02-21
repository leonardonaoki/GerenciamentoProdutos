package com.gestaopedidos.gestao.pedidos.infrastructure.controller;

import com.gestaopedidos.gestao.pedidos.domain.dto.ResponseDTO;
import com.gestaopedidos.gestao.pedidos.domain.dto.request.InsertPedidoDTO;
import com.gestaopedidos.gestao.pedidos.domain.dto.request.LocalizacaoDTO;
import com.gestaopedidos.gestao.pedidos.domain.dto.request.UpdatePedidoDTO;
import com.gestaopedidos.gestao.pedidos.domain.dto.responses.ListPedidosResponseDTO;
import com.gestaopedidos.gestao.pedidos.domain.dto.responses.PedidosAndMessagesResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface IControllerDocumentation {
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
    ResponseEntity<ListPedidosResponseDTO> listarPedidos(
            @RequestParam(value = "_offset", required = false, defaultValue = "0") int offset,
            @RequestParam(value = "_limit", required = false, defaultValue = "10") int limit);

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
    ResponseEntity<PedidosAndMessagesResponseDTO> listarPedidosPorId(@PathVariable(value = "id", required = true) int id);

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
    ResponseEntity<PedidosAndMessagesResponseDTO> criarPedido(@Valid @RequestBody(required = true) InsertPedidoDTO dto);

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
    ResponseEntity<PedidosAndMessagesResponseDTO> atualizarPedidoPorId(
            @PathVariable(value = "id", required = true) long id, @Valid @RequestBody(required = true) UpdatePedidoDTO dto);

    @Operation(
            operationId = "atualizaLocalizacao",
            summary = "Atualiza  um pedido",
            description = "Esse endpoint atualiza a localizacao exata do pedido",
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
	ResponseEntity<PedidosAndMessagesResponseDTO> atualizaLocalizacao(@PathVariable(value = "id", required = true)  long id, @Valid LocalizacaoDTO dto);

    @Operation(
            operationId = "concluiEntregaPedido",
            summary = "Conclui pedido",
            description = "Esse endpoint conclui o pedido",
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
	ResponseEntity<PedidosAndMessagesResponseDTO> concluiEntregaPedido(@PathVariable(value = "id", required = true)  long id);

    @Operation(
            operationId = "AtualizaEntregadorPedido",
            summary = "Atualiza o entregador no pedido",
            description = "Esse endpoint atualiza um pedido informando o entregador e muda o statu",
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
	ResponseEntity<PedidosAndMessagesResponseDTO> atualizaEntregadorPedido(@PathVariable(value = "id", required = true) long id, @Valid long idEntregador);
}
