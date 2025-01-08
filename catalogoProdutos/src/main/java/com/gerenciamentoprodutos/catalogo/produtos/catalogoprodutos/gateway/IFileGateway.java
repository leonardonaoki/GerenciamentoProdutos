package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.gateway;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.entity.CsvFile;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.exception.SystemBaseHandleException;

public interface IFileGateway {
    public void salvar(CsvFile csvFile) throws SystemBaseHandleException;
}
