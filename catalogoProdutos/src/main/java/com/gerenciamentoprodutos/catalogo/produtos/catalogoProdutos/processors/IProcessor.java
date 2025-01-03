package com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.processors;

import org.springframework.web.multipart.MultipartFile;

public interface IProcessor {
    void Process(MultipartFile file) throws Exception;
}
