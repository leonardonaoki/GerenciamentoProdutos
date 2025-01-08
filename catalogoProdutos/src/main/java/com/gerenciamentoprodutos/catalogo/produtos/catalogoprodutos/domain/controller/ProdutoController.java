package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.controller;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.app.ImportarProdutoUseCase;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.ResponseDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.entity.CsvFile;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.exception.SystemBaseHandleException;
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
    private final ImportarProdutoUseCase produtoUseCase;
    public static final String NOME_ARQUIVO_PADRAO = "dados.csv";
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
