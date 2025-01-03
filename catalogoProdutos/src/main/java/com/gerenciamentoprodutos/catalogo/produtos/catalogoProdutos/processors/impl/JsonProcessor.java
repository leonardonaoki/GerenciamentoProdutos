package com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.processors.impl;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.processors.IProcessor;
import org.springframework.web.multipart.MultipartFile;

public class JsonProcessor implements IProcessor {
    @Override
    public void Process(MultipartFile file) throws Exception{
        System.out.println("Processei com o json");
    };
}
