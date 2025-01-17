package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.controller;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.app.*;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.request.InsertAndUpdateProdutoDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.responses.ProductAndMessagesResponseDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.responses.ListProdutosResponseDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.ProdutoDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.ResponseDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.entity.CsvFile;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.exception.SystemBaseHandleException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.function.Consumer;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
@Slf4j
public class ProdutosController {
    private final ImportarProdutosUseCase produtoUseCase;
    private final ListarProdutosUseCase listarProdutosUseCase;
    private final ListarProdutoPorIdUseCase listarProdutoPorIdUseCase;
    private final CriarProdutoUseCase criarProdutoUseCase;
    private final AtualizaProdutoPorIdUseCase atualizaProdutoUseCase;
    private final DeletaProdutoPorIdUseCase deletaProdutoPorIdUseCase;
    public static final String NOME_ARQUIVO_PADRAO = "dados.csv";
    @Value("${carga.input-path}")
    private String diretorio;

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
    public ResponseEntity<ListProdutosResponseDTO> listarProdutos(
            @RequestParam(value = "_offset",required = false,defaultValue = "0") int offset,
            @RequestParam(value = "_limit",required = false,defaultValue = "10") int limit){
        if (offset < 0) {
            offset = 0;
        }
        if (limit < 0) {
            limit = 10;
        }
        return ResponseEntity.ok().body(listarProdutosUseCase.listarProdutos(offset,limit));
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
    public ResponseEntity<ProductAndMessagesResponseDTO> listarProdutosPorId(@PathVariable(value = "id",required = true) int id){
        try{
            ProdutoDTO produtoEncontrado = listarProdutoPorIdUseCase.listarProdutoPorId(id);
            return ResponseEntity.ok().body(
                    new ProductAndMessagesResponseDTO(
                            produtoEncontrado,
                            HttpStatus.OK.value(),
                            "Sucesso")
            );
        }
        catch(SystemBaseHandleException s){
            return ResponseEntity.internalServerError().body(
                    new ProductAndMessagesResponseDTO(
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
    public ResponseEntity<ProductAndMessagesResponseDTO> criarProduto(
            @Valid @RequestBody(required = true) InsertAndUpdateProdutoDTO dto
    ) {
        ProdutoDTO produtoInserido = criarProdutoUseCase.criarProduto(dto);
        return ResponseEntity.ok().body(
                new ProductAndMessagesResponseDTO(
                        produtoInserido,
                        HttpStatus.CREATED.value(),
                        "Sucesso")
        );
    }
    @PutMapping("/{id}")
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
    public ResponseEntity<ProductAndMessagesResponseDTO> atualizarProdutoPorId(
            @PathVariable(value = "id",required = true) long id, @Valid @RequestBody(required = true) InsertAndUpdateProdutoDTO dto
    ) {
        try{
            ProdutoDTO produtoAtualizado = atualizaProdutoUseCase.atualizaProdutoPorId(id,dto);
            return ResponseEntity.ok().body(
                    new ProductAndMessagesResponseDTO(
                            produtoAtualizado,
                            HttpStatus.OK.value(),
                            "Produto atualizado com sucesso!")
            );
        }
        catch(SystemBaseHandleException s){
            return ResponseEntity.internalServerError().body(
                    new ProductAndMessagesResponseDTO(
                            null,
                            HttpStatus.BAD_REQUEST.value(),
                            s.getMessage()));
        }
    }
    @DeleteMapping("/{id}")
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
    public ResponseEntity<ResponseDTO> deletaProdutoPorId(
            @PathVariable(value = "id",required = true) long id)
    {
        try{
            deletaProdutoPorIdUseCase.deletaProdutoPorId(id);
            return ResponseEntity.ok().body(new ResponseDTO(
                    HttpStatus.OK.value(),
                    "Deletado com sucesso!"));
        }catch(SystemBaseHandleException s){
            return ResponseEntity.internalServerError().body(
                    new ResponseDTO(
                            HttpStatus.BAD_REQUEST.value(),
                            s.getMessage()));
        }


    }
    @PostMapping("/importar-produtos")
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
    public ResponseEntity<ResponseDTO> importaProdutosCSV(@RequestParam("file") MultipartFile file) throws SystemBaseHandleException {
        if(file.isEmpty())
            return ResponseEntity.badRequest().body(new ResponseDTO(HttpStatus.BAD_REQUEST.value(),"Arquivo vazio!"));

        String nomeDoArquivo = file.getOriginalFilename();
        if (!nomeDoArquivo.toUpperCase().endsWith(".CSV")) {
            return ResponseEntity.badRequest().body(new ResponseDTO(HttpStatus.BAD_REQUEST.value(),"Arquivo inválido!"));
        }
        try {
            CsvFile csvFile = new CsvFile(NOME_ARQUIVO_PADRAO , diretorio, file.getBytes());
            ResponseDTO response = produtoUseCase.salvar(csvFile);
            HttpStatus status = HttpStatus.valueOf(response.HttpStatusCode());
            return ResponseEntity.status(status).body(new ResponseDTO(status.value(),response.Message()));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ocorreu um erro ao importar os produtos do csv."));
        }
    }
}