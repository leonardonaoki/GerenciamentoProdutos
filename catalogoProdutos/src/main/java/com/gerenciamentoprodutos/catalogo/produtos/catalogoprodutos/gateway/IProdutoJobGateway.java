package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.gateway;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.ResponseDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.entity.CsvFile;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.exception.SystemBaseHandleException;

public interface IProdutoJobGateway {
    public ResponseDTO execute(CsvFile csvFile) throws SystemBaseHandleException;
}
