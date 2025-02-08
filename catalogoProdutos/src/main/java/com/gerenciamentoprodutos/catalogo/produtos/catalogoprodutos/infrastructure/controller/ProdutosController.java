package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.infrastructure.controller;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.app.*;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.request.InsertAndUpdateProdutoDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.responses.ProductAndMessagesResponseDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.responses.ListProdutosResponseDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.ProdutoDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.ResponseDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.entity.CsvFile;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.entity.ProdutosDomain;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.mapper.IProdutoMapper;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.exception.SystemBaseHandleException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
@Slf4j
public class ProdutosController implements IControllerDocumentation {
    private final ImportarProdutosUseCase importarProdutosUseCase;
    private final ListarProdutosUseCase listarProdutosUseCase;
    private final ListarProdutoPorIdUseCase listarProdutoPorIdUseCase;
    private final CriarProdutoUseCase criarProdutoUseCase;
    private final AtualizaProdutoPorIdUseCase atualizaProdutoUseCase;
    private final DeletaProdutoPorIdUseCase deletaProdutoPorIdUseCase;

    private final IProdutoMapper produtoMapper;

    public static final String NOME_ARQUIVO_PADRAO = "dados.csv";
    @Value("${carga.input-path}")
    private String diretorio;

    @GetMapping()
    @Override
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
    @Override
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
    @Override
    public ResponseEntity<ProductAndMessagesResponseDTO> criarProduto(
            @Valid @RequestBody(required = true) InsertAndUpdateProdutoDTO dto
    ) {
        ProdutosDomain produtosDomain = produtoMapper.toDomain(dto);
        ProdutoDTO produtoInserido = criarProdutoUseCase.criarProduto(produtosDomain);
        return ResponseEntity.ok().body(
                new ProductAndMessagesResponseDTO(
                        produtoInserido,
                        HttpStatus.CREATED.value(),
                        "Sucesso")
        );
    }
    @PutMapping("/{id}")
    @Override
    public ResponseEntity<ProductAndMessagesResponseDTO> atualizarProdutoPorId(
            @PathVariable(value = "id",required = true) long id, @Valid @RequestBody(required = true) InsertAndUpdateProdutoDTO dto
    ) {
        ProdutosDomain produtosDomain = produtoMapper.toDomain(dto);
        try{
            ProdutoDTO produtoAtualizado = atualizaProdutoUseCase.atualizaProdutoPorId(id,produtosDomain);
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
    @Override
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
    @Override
    public ResponseEntity<ResponseDTO> importaProdutosCSV(@RequestParam("file") MultipartFile file){
        if(file.isEmpty())
            return ResponseEntity.badRequest().body(new ResponseDTO(HttpStatus.BAD_REQUEST.value(),"Arquivo vazio!"));

        String nomeDoArquivo = file.getOriginalFilename();
        if (!nomeDoArquivo.toUpperCase().endsWith(".CSV")) {
            return ResponseEntity.badRequest().body(new ResponseDTO(HttpStatus.BAD_REQUEST.value(),"Arquivo inválido!"));
        }
        try {
            CsvFile csvFile = new CsvFile(NOME_ARQUIVO_PADRAO , diretorio, file.getBytes());
            ResponseDTO response = importarProdutosUseCase.salvar(csvFile);
            HttpStatus status = HttpStatus.valueOf(response.HttpStatusCode());
            return ResponseEntity.status(status).body(new ResponseDTO(status.value(),response.Message()));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ocorreu um erro ao importar os produtos do csv."));
        }
        catch (SystemBaseHandleException e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erro " + e.getMessage()));
        }
    }
}