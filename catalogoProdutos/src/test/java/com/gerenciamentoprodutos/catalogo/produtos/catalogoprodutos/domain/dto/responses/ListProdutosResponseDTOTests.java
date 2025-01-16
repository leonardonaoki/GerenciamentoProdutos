package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.responses;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.ProdutoDTO;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class ListProdutosResponseDTOTests {
    @Test
    void deveCriarListProdutosResponseDTOCorretamente(){
        List<ProdutoDTO> listaProdutos = new ArrayList<>();
        ProdutoDTO produto = new ProdutoDTO(1,"DescricaoTeste",new BigDecimal(200),300);
        listaProdutos.add(produto);
        Page<ProdutoDTO> pageDTO = new PageImpl<>(listaProdutos);

        ListProdutosResponseDTO responseTeste = new ListProdutosResponseDTO(pageDTO);

        assertNotNull(responseTeste);
        assertEquals(listaProdutos.size(), responseTeste.Produtos().getContent().size());
    }
}
