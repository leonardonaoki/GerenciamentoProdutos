package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class CatalogoProdutosApplicationTests {
    @Test
    void deveExecutarAplicaoNormalmente(){
        assertDoesNotThrow(() -> CatalogoProdutosApplication.main(new String[]{}));
    }
}