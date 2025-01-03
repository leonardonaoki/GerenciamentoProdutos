package com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.config;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.mapper.ProdutoMapper;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.mapper.impl.ProdutoMapperImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    ProdutoMapper produtoMapper(){
        return new ProdutoMapperImpl();
    }
}
