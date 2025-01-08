package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class CsvFileTests {
    @Test
    void deveCriarESetarAsPropriedadesCorretamente(){
        String nomeArquivoTeste = "dados.csv";
        String diretorioTeste = "C:\\Entrada";
        byte[] bytesTeste = new byte[]{};

        CsvFile csv = new CsvFile(nomeArquivoTeste,diretorioTeste,bytesTeste);

        assertEquals(nomeArquivoTeste,csv.getNomeArquivo());
        assertEquals(diretorioTeste,csv.getDiretorio());
        assertEquals(bytesTeste,csv.getBinario());
    }
}
