package com.gerenciamentoclientes.clientes.infrastructure.controller;

import com.gerenciamentoclientes.clientes.domain.dto.ResponseDTO;
import com.gerenciamentoclientes.clientes.domain.dto.request.InsertAndUpdateClienteDTO;
import com.gerenciamentoclientes.clientes.domain.dto.response.ClienteAndMessagesResponseDTO;
import com.gerenciamentoclientes.clientes.domain.dto.response.ListaClientesResponseDTO;
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
            operationId = "ListarClientes",
            summary = "Lista os clientes existentes de forma paginada",
            description = "Esse endpoint lista os clientes existentes de forma paginada",
            tags = "CatalogoClientes",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ListaClientesResponseDTO.class)
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
    ResponseEntity<ListaClientesResponseDTO> listarClientes(
            @RequestParam(value = "_offset", required = false, defaultValue = "0") int offset,
            @RequestParam(value = "_limit", required = false, defaultValue = "10") int limit);

    @Operation(
            operationId = "ListarClientePorId",
            summary = "Lista o cliente por ID",
            description = "Esse endpoint lista o cliente por ID",
            tags = "CatalogoClientes",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ClienteAndMessagesResponseDTO.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ClienteAndMessagesResponseDTO.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ClienteAndMessagesResponseDTO.class)
                                    )
                            }
                    )
            }
    )
    ResponseEntity<ClienteAndMessagesResponseDTO> listarClientePorId(@PathVariable(value = "id", required = true) long id);
    @Operation(
            operationId = "CriarCliente",
            summary = "Cria um cliente",
            description = "Esse endpoint cria um cliente",
            tags = "CatalogoClientes",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Created",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ClienteAndMessagesResponseDTO.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ClienteAndMessagesResponseDTO.class)
                                    )
                            }
                    )
            }
    )
    ResponseEntity<ClienteAndMessagesResponseDTO> criarCliente(@Valid @RequestBody(required = true) InsertAndUpdateClienteDTO dto);
    @Operation(
            operationId = "AtualizarCliente",
            summary = "Atualiza um cliente",
            description = "Esse endpoint atualiza um cliente",
            tags = "CatalogoClientes",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ClienteAndMessagesResponseDTO.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ClienteAndMessagesResponseDTO.class)
                                    )
                            }
                    )
            }
    )
    ResponseEntity<ClienteAndMessagesResponseDTO> atualizarClientePorId(
            @PathVariable(value = "id", required = true) long id, @Valid @RequestBody(required = true) InsertAndUpdateClienteDTO dto);
    @Operation(
            operationId = "DeletarCliente",
            summary = "Deleta um cliente",
            description = "Esse endpoint deleta um cliente",
            tags = "CatalogoClientes",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ResponseDTO.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ResponseDTO.class)
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
    ResponseEntity<ResponseDTO> deletaClientePorId(@PathVariable(value = "id", required = true) long id);
}
