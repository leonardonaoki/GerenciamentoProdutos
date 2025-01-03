package com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.service;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.dto.ProdutoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProdutoService {
    List<ProdutoDTO> listarProdutos();
    ResponseEntity<?> importarProdutosBatch(MultipartFile file);
}
