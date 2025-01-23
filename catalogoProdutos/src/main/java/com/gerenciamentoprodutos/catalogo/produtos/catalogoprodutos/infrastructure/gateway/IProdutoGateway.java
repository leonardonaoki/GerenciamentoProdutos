package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.infrastructure.gateway;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.consumer.AtualizacaoProdutosDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.request.InsertAndUpdateProdutoDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.ProdutoDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.exception.SystemBaseHandleException;
import org.springframework.data.domain.Page;

public interface IProdutoGateway {
    Page<ProdutoDTO> listarProdutos(int offset,int limit);
    ProdutoDTO listarProdutoPorId(long id) throws SystemBaseHandleException;
    ProdutoDTO criarProduto(InsertAndUpdateProdutoDTO dto);
    ProdutoDTO atualizarProdutoPorId(long id,InsertAndUpdateProdutoDTO dto) throws SystemBaseHandleException;
    void atualizarListaProdutos(AtualizacaoProdutosDTO dto);
    void deletarProdutoPorId(long id) throws SystemBaseHandleException;
}
