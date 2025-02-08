package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.infrastructure.gateway;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.entity.consumer.AtualizacaoProdutosDomain;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.ProdutoDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.entity.ProdutosDomain;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.exception.SystemBaseHandleException;
import org.springframework.data.domain.Page;

public interface IProdutoGateway {
    Page<ProdutoDTO> listarProdutos(int offset,int limit);
    ProdutoDTO listarProdutoPorId(long id) throws SystemBaseHandleException;
    ProdutoDTO criarProduto(ProdutosDomain dto);
    ProdutoDTO atualizarProdutoPorId(long id,ProdutosDomain dto) throws SystemBaseHandleException;
    void atualizarListaProdutos(AtualizacaoProdutosDomain dto);
    void deletarProdutoPorId(long id) throws SystemBaseHandleException;
}
