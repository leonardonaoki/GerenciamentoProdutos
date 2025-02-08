package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.infrastructure.controller;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.ResponseDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.request.InsertAndUpdateProdutoDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.responses.ListProdutosResponseDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.responses.ProductAndMessagesResponseDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.exception.SystemBaseHandleException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public interface IControllerDocumentation {
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
                                            schema = @Schema(implementation = ListProdutosResponseDTO.class)
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
    ResponseEntity<ListProdutosResponseDTO> listarProdutos(
            @RequestParam(value = "_offset",required = false,defaultValue = "0") int offset,
            @RequestParam(value = "_limit",required = false,defaultValue = "10") int limit);

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
                                            schema = @Schema(implementation = ProductAndMessagesResponseDTO.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ProductAndMessagesResponseDTO.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ProductAndMessagesResponseDTO.class)
                                    )
                            }
                    )
            }
    )
    ResponseEntity<ProductAndMessagesResponseDTO> listarProdutosPorId(@PathVariable(value = "id",required = true) int id);
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
                                            schema = @Schema(implementation = ProductAndMessagesResponseDTO.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ProductAndMessagesResponseDTO.class)
                                    )
                            }
                    )
            }
    )
    ResponseEntity<ProductAndMessagesResponseDTO> criarProduto(@Valid @RequestBody(required = true) InsertAndUpdateProdutoDTO dto);
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
                                            schema = @Schema(implementation = ProductAndMessagesResponseDTO.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ProductAndMessagesResponseDTO.class)
                                    )
                            }
                    )
            }
    )
    ResponseEntity<ProductAndMessagesResponseDTO> atualizarProdutoPorId(
            @PathVariable(value = "id",required = true) long id, @Valid @RequestBody(required = true) InsertAndUpdateProdutoDTO dto);

    @Operation(
            operationId = "DeletarProduto",
            summary = "Deleta um produto",
            description = "Esse endpoint deleta um produto",
            tags = "CatalogoProdutos",
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
    ResponseEntity<ResponseDTO> deletaProdutoPorId(
            @PathVariable(value = "id",required = true) long id);

    @Operation(
            operationId = "ImportaProdutosCSV",
            summary = "Importação de produtos através de arquivo csv",
            description = "Esse endpoint importa produtos através de um arquivo csv",
            tags = "CatalogoProdutos",
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
                            description = "Bad request",
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
    ResponseEntity<ResponseDTO> importaProdutosCSV(@RequestParam("file") MultipartFile file) throws SystemBaseHandleException;
}
