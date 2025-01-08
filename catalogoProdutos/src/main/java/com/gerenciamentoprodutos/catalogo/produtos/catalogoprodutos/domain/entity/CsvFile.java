package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CsvFile {
    private String nomeArquivo;
    private String diretorio;
    private byte[] binario;
}
