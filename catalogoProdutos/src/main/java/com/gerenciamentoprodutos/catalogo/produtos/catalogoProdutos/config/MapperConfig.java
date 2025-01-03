package com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.config;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.mapper.IProdutoMapper;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.mapper.impl.ProdutoMapperImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    IProdutoMapper produtoMapper(){
        return new ProdutoMapperImpl();
    }
}
