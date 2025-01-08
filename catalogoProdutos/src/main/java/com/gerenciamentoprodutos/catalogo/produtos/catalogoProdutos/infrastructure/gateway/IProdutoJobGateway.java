package com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.infrastructure.gateway;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.domain.entity.CsvFile;
import org.springframework.http.ResponseEntity;

public interface IProdutoJobGateway {
    public ResponseEntity<?> execute(CsvFile csvFile);
}
