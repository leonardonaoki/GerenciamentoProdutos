package com.gerenciamentoclientes.clientes.infrastructure.controller;

import com.gerenciamentoclientes.clientes.app.*;
import com.gerenciamentoclientes.clientes.domain.dto.ClienteDTO;
import com.gerenciamentoclientes.clientes.domain.dto.ResponseDTO;
import com.gerenciamentoclientes.clientes.domain.dto.request.InsertAndUpdateClienteDTO;
import com.gerenciamentoclientes.clientes.domain.dto.response.ClienteAndMessagesResponseDTO;
import com.gerenciamentoclientes.clientes.domain.dto.response.ListaClientesResponseDTO;
import com.gerenciamentoclientes.clientes.exception.SystemBaseHandleException;
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

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
@Slf4j
public class ClienteController {

    private final ListarClientesUseCase listarClientesUseCase;
    private final ListarClientePorIdUseCase listarClientePorIdUseCase;
    private final CriarClienteUseCase criarClienteUseCase;
    private final AtualizaClientePorIdUseCase atualizaClienteUseCase;
    private final DeletaClientePorIdUseCase deletaClientePorIdUseCase;

    @GetMapping()
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
    public ResponseEntity<ListaClientesResponseDTO> listarClientes(
            @RequestParam(value = "_offset", required = false, defaultValue = "0") int offset,
            @RequestParam(value = "_limit", required = false, defaultValue = "10") int limit) {
        if (offset < 0) {
            offset = 0;
        }
        if (limit < 0) {
            limit = 10;
        }
        return ResponseEntity.ok().body(listarClientesUseCase.listarClientes(offset, limit));
    }

    @GetMapping("/{id}")
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
    public ResponseEntity<ClienteAndMessagesResponseDTO> listarClientePorId(@PathVariable(value = "id", required = true) long id) {
        try {
            ClienteDTO clienteEncontrado = listarClientePorIdUseCase.listarClientePorId(id);
            return ResponseEntity.ok().body(
                    new ClienteAndMessagesResponseDTO(
                            clienteEncontrado,
                            HttpStatus.OK.value(),
                            "Sucesso")
            );
        } catch (SystemBaseHandleException s) {
            return ResponseEntity.internalServerError().body(
                    new ClienteAndMessagesResponseDTO(
                            null,
                            HttpStatus.BAD_REQUEST.value(),
                            s.getMessage()));
        }
    }

    @PostMapping()
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
    public ResponseEntity<ClienteAndMessagesResponseDTO> criarCliente(
            @Valid @RequestBody(required = true) InsertAndUpdateClienteDTO dto) {
        ClienteDTO clienteInserido = criarClienteUseCase.criarCliente(dto);
        return ResponseEntity.ok().body(
                new ClienteAndMessagesResponseDTO(
                        clienteInserido,
                        HttpStatus.CREATED.value(),
                        "Sucesso")
        );
    }

    @PutMapping("/{id}")
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
    public ResponseEntity<ClienteAndMessagesResponseDTO> atualizarClientePorId(
            @PathVariable(value = "id", required = true) long id, @Valid @RequestBody(required = true) InsertAndUpdateClienteDTO dto) {
        try {
            ClienteDTO clienteAtualizado = atualizaClienteUseCase.atualizaClientePorId(id, dto);
            return ResponseEntity.ok().body(
                    new ClienteAndMessagesResponseDTO(
                            clienteAtualizado,
                            HttpStatus.OK.value(),
                            "Cliente atualizado com sucesso!")
            );
        } catch (SystemBaseHandleException s) {
            return ResponseEntity.internalServerError().body(
                    new ClienteAndMessagesResponseDTO(
                            null,
                            HttpStatus.BAD_REQUEST.value(),
                            s.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
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
    public ResponseEntity<ResponseDTO> deletaClientePorId(
            @PathVariable(value = "id", required = true) long id) {
        try {
            deletaClientePorIdUseCase.deletaClientePorId(id);
            return ResponseEntity.ok().body(new ResponseDTO(
                    HttpStatus.OK.value(),
                    "Deletado com sucesso!"));
        } catch (SystemBaseHandleException s) {
            return ResponseEntity.internalServerError().body(
                    new ResponseDTO(
                            HttpStatus.BAD_REQUEST.value(),
                            s.getMessage()));
        }
    }
}
