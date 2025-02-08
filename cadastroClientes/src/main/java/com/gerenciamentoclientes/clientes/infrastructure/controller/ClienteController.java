package com.gerenciamentoclientes.clientes.infrastructure.controller;

import com.gerenciamentoclientes.clientes.app.*;
import com.gerenciamentoclientes.clientes.domain.dto.ClienteDTO;
import com.gerenciamentoclientes.clientes.domain.dto.ResponseDTO;
import com.gerenciamentoclientes.clientes.domain.dto.request.InsertAndUpdateClienteDTO;
import com.gerenciamentoclientes.clientes.domain.dto.response.ClienteAndMessagesResponseDTO;
import com.gerenciamentoclientes.clientes.domain.dto.response.ListaClientesResponseDTO;
import com.gerenciamentoclientes.clientes.domain.entity.ClienteDomain;
import com.gerenciamentoclientes.clientes.domain.mapper.IClienteMapper;
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
public class ClienteController implements IControllerDocumentation {

    private final ListarClientesUseCase listarClientesUseCase;
    private final ListarClientePorIdUseCase listarClientePorIdUseCase;
    private final CriarClienteUseCase criarClienteUseCase;
    private final AtualizaClientePorIdUseCase atualizaClienteUseCase;
    private final DeletaClientePorIdUseCase deletaClientePorIdUseCase;
    private final IClienteMapper clienteMapper;
    @GetMapping()
    @Override
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
    @Override
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
    @Override
    public ResponseEntity<ClienteAndMessagesResponseDTO> criarCliente(
            @Valid @RequestBody(required = true) InsertAndUpdateClienteDTO dto) {
        ClienteDomain domain = clienteMapper.toDomain(dto);
        ClienteDTO clienteInserido = criarClienteUseCase.criarCliente(domain);
        return ResponseEntity.ok().body(
                new ClienteAndMessagesResponseDTO(
                        clienteInserido,
                        HttpStatus.CREATED.value(),
                        "Sucesso")
        );
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<ClienteAndMessagesResponseDTO> atualizarClientePorId(
            @PathVariable(value = "id", required = true) long id, @Valid @RequestBody(required = true) InsertAndUpdateClienteDTO dto) {
        ClienteDomain domain = clienteMapper.toDomain(dto);
        try {
            ClienteDTO clienteAtualizado = atualizaClienteUseCase.atualizaClientePorId(id, domain);
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
    @Override
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
