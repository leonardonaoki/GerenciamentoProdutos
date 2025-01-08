package com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.infrastructure.gateway;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.domain.entity.CsvFile;

public interface IFileGateway {
    public void salvar(CsvFile csvFile);
}
