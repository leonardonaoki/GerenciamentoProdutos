package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.mapper;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.ProdutoDTO;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.infrastructure.entityjpa.ProdutoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IProdutoMapper {
    @Mapping(source = "entity.id", target = "Id")
    @Mapping(source = "entity.descricao", target = "Descricao")
    @Mapping(source = "entity.preco", target = "Preco")
    @Mapping(source = "entity.quantidadeEstoque", target = "QuantidadeEstoque")
    ProdutoDTO toDTO(ProdutoEntity produto);
}