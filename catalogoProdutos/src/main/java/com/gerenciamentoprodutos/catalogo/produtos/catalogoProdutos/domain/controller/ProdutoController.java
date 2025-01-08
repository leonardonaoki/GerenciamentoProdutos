package com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.domain.controller;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.app.ImportarProdutoUseCase;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.domain.dto.ResponseDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.domain.entity.CsvFile;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.infrastructure.gateway.IFileGateway;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.infrastructure.gateway.IProdutoJobGateway;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/importar-produtos")
@RequiredArgsConstructor
@Slf4j
public class ProdutoController {
    private final IFileGateway fileGateway;
    private final IProdutoJobGateway produtoJobGateway;

    private static final String NOME_ARQUIVO_PADRAO = "dados.csv";
    @Value("${carga.input-path}")
    private String diretorio;

    @PostMapping()
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
    public ResponseEntity<?> importaProdutosCSV(@RequestParam("file") MultipartFile file){
        if(file.isEmpty())
            return ResponseEntity.badRequest().body(new ResponseDTO("Arquivo vazio!"));

        String nomeDoArquivo = file.getOriginalFilename();
        if (nomeDoArquivo == null || !nomeDoArquivo.toUpperCase().endsWith(".CSV")) {
            return ResponseEntity.badRequest().body(new ResponseDTO("Arquivo inválido!"));
        }
        try {
            CsvFile csvFile = new CsvFile(NOME_ARQUIVO_PADRAO , diretorio, file.getBytes());
            return new ImportarProdutoUseCase(fileGateway,produtoJobGateway).salvar(csvFile);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().body(new ResponseDTO("Ocorreu um erro ao importar os produtos do csv."));
        }
    }
}
