package com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.app;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.domain.dto.ResponseDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.domain.entity.CsvFile;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.infrastructure.gateway.IFileGateway;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.infrastructure.gateway.IProdutoJobGateway;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImportarProdutoUseCase {
    private final IFileGateway fileGateway;
    private final IProdutoJobGateway produtoJobGateway;

    public ResponseEntity<?> salvar(CsvFile csvFile) {
        try{
            fileGateway.salvar(csvFile);
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body(new ResponseDTO("Não foi possível realizar o Upload do Arquivo!"));
        }

        try{
            return produtoJobGateway.execute(csvFile);
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body(new ResponseDTO("Não foi possível processar o arquivo!"));
        }
    }
}
