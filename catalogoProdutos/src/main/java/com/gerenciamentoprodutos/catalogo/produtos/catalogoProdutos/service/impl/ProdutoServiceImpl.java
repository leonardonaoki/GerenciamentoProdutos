package com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.service.impl;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.dto.ProdutoDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.infrastructure.repository.ProdutoRepository;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.mapper.ProdutoMapper;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoServiceImpl implements ProdutoService {
    private final ProdutoRepository produtoRepo;
    private final ProdutoMapper produtoMapper;

    @Override
    public List<ProdutoDTO> listarProdutos(){
        return produtoRepo.findAll().stream().map(p -> produtoMapper.toDTO(p)).toList();
    }
}
