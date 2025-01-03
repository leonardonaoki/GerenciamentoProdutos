package com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.controller;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.dto.ProdutoDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.service.ProdutoService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/produto")
@RequiredArgsConstructor
public class ProdutoController {
    private final ProdutoService produtoService;

    @GetMapping()
    public ResponseEntity<List<ProdutoDTO>> listaProdutos(){
        return ResponseEntity.ok(produtoService.listarProdutos());
    }

    @PostMapping()
    public ResponseEntity<?> importarProdutosBatch(MultipartFile file){
        return produtoService.importarProdutosBatch(file);
    }
}
